package com.example.varchas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // UI Components - Input Section
    private EditText etLatitude, etLongitude;
    private RadioGroup rgCropType;
    private RadioButton rbWheat, rbRice;
    private Button btnCalculate;
    private ProgressBar progressBar;
    private CardView cvResults;

    // UI Components - Result Section
    private TextView tvScore, tvDecision;
    private TextView tvLoanLimit, tvRevenue, tvYield;
    private TextView tvCropHealth, tvWeather, tvSoilStatus, tvNDVI;
    private TextView tvReasons;

    // API Configuration - Update for physical device
    private static final String BASE_URL = "http://10.0.2.2:5000"; // For emulator
//     private static final String BASE_URL = "http://YOUR_COMPUTER_IP:8000"; // For physical device

    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String farmerPhone = getIntent().getStringExtra("phone");

        // Initialize RequestQueue for API calls
        requestQueue = Volley.newRequestQueue(this);

        // Initialize all UI components
        initializeViews();

        // Set button click listener
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCreditScore();
            }
        });
    }

    private void initializeViews() {
        // Input fields
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        rgCropType = findViewById(R.id.rgCropType);
        rbWheat = findViewById(R.id.rbWheat);
        rbRice = findViewById(R.id.rbRice);

        // Button and progress
        btnCalculate = findViewById(R.id.btnCalculate);
        progressBar = findViewById(R.id.progressBar);
        cvResults = findViewById(R.id.cvResults);

        // Result views - matching your backend response fields exactly
        tvScore = findViewById(R.id.tvScore);
        tvDecision = findViewById(R.id.tvDecision);
        tvLoanLimit = findViewById(R.id.tvLoanLimit);
        tvRevenue = findViewById(R.id.tvRevenue);
        tvYield = findViewById(R.id.tvYield);
        tvCropHealth = findViewById(R.id.tvCropHealth);
        tvWeather = findViewById(R.id.tvWeather);
        tvSoilStatus = findViewById(R.id.tvSoilStatus);
        tvNDVI = findViewById(R.id.tvNDVI);
        tvReasons = findViewById(R.id.tvReasons);
    }

    private void calculateCreditScore() {
        // Get input values
        String latStr = etLatitude.getText().toString().trim();
        String lonStr = etLongitude.getText().toString().trim();

        // Validate inputs
        if (latStr.isEmpty() || lonStr.isEmpty()) {
            Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latStr);
            longitude = Double.parseDouble(lonStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid coordinates format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate coordinate ranges
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            Toast.makeText(this, "Coordinates out of valid range", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected crop (must be lowercase to match backend)
        String crop = rbWheat.isChecked() ? "wheat" : "rice";

        // Show loading state
        showLoading(true);

        // Make API call
        makeApiRequest(latitude, longitude, crop);
    }

    private void makeApiRequest(double latitude, double longitude, String crop) {
        String url = BASE_URL + "/credit-score";

        // Create JSON request body matching your CreditRequest model
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("lat", latitude);
            requestBody.put("lon", longitude);
            requestBody.put("crop", crop);  // "wheat" or "rice" (lowercase)
        } catch (JSONException e) {
            e.printStackTrace();
            showError("Error creating request");
            showLoading(false);
            return;
        }

        // Create POST request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showLoading(false);
                        displayResults(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showLoading(false);
                        String errorMsg = "Network error. Please check your connection.";

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            errorMsg = "Server error: " + statusCode;

                            // Log the error for debugging
                            if (error.networkResponse.data != null) {
                                try {
                                    String errorData = new String(error.networkResponse.data);
                                    System.out.println("Error response: " + errorData);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (error.getMessage() != null) {
                            errorMsg = error.getMessage();
                        }

                        showError(errorMsg);
                    }
                }
        );

        // Add request to queue
        requestQueue.add(jsonObjectRequest);
    }

    private void displayResults(JSONObject response) {
        try {
            // Parse response - matching your exact backend field names
            int score = response.getInt("score");                    // from calculate_score()
            String decision = response.getString("decision");         // "Approve" or "High Risk"
            int loanLimit = response.getInt("loan_limit");          // from loan_decision()
            int revenue = response.getInt("revenue");               // from estimate_revenue()
            String yield = response.getString("estimated_yield");   // "X.XX tons" format
            double ndvi = response.getDouble("ndvi");               // from get_ndvi()
            String cropHealth = response.getString("crop_health");  // "Good", "Moderate", or "Poor"
            String weather = response.getString("weather");         // "Normal", "Drought Risk", or "Flood Risk"
            String soilStatus = response.getString("soil_status");  // "Healthy Soil", "Dry Soil", or "Soil Data Unavailable"

            // Parse reasons array from explain_score()
            StringBuilder reasonsText = new StringBuilder();
            JSONArray reasonsArray = response.getJSONArray("reasons");
            for (int i = 0; i < reasonsArray.length(); i++) {
                reasonsText.append("• ").append(reasonsArray.getString(i));
                if (i < reasonsArray.length() - 1) {
                    reasonsText.append("\n");
                }
            }

            // Display score (0-100)
            tvScore.setText(String.valueOf(score));

            // Display decision with appropriate styling
            // Backend returns "Approve" or "High Risk"
            if (decision.equals("Approve")) {
                tvDecision.setText("✅ Approved");
                tvDecision.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvDecision.setText("⚠️ " + decision);
                tvDecision.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            }

            // Format and display financial data
            tvLoanLimit.setText(formatIndianCurrency(loanLimit));
            tvRevenue.setText(formatIndianCurrency(revenue));
            tvYield.setText(yield);  // Already formatted as "X.XX tons" from backend
            tvNDVI.setText(String.valueOf(ndvi));

            // Display health indicators with color coding
            tvCropHealth.setText(cropHealth);
            setCropHealthColor(cropHealth);  // "Good", "Moderate", "Poor"

            tvWeather.setText(weather);
            setWeatherColor(weather);  // "Normal", "Drought Risk", "Flood Risk"

            tvSoilStatus.setText(soilStatus);
            setSoilStatusColor(soilStatus);  // "Healthy Soil", "Dry Soil", "Soil Data Unavailable"

            // Display score factors
            tvReasons.setText(reasonsText.toString());

            // Show results card with smooth fade-in animation
            cvResults.setVisibility(View.VISIBLE);
            cvResults.setAlpha(0f);
            cvResults.animate().alpha(1f).setDuration(300).start();

        } catch (JSONException e) {
            e.printStackTrace();
            showError("Error parsing response: " + e.getMessage());
        }
    }

    private String formatIndianCurrency(int amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return format.format(amount);
    }

    private void setCropHealthColor(String health) {
        int color;
        // Match backend crop_health() function values
        switch (health) {
            case "Good":
                color = getResources().getColor(android.R.color.holo_green_dark);
                break;
            case "Moderate":
                color = getResources().getColor(android.R.color.holo_orange_dark);
                break;
            case "Poor":
                color = getResources().getColor(android.R.color.holo_red_dark);
                break;
            default:
                color = getResources().getColor(android.R.color.darker_gray);
                break;
        }
        tvCropHealth.setTextColor(color);
    }

    private void setWeatherColor(String weather) {
        int color;
        // Match backend rainfall_status() function values
        if (weather.equals("Normal")) {
            color = getResources().getColor(android.R.color.holo_green_dark);
        } else if (weather.equals("Drought Risk")) {
            color = getResources().getColor(android.R.color.holo_orange_dark);
        } else if (weather.equals("Flood Risk")) {
            color = getResources().getColor(android.R.color.holo_red_dark);
        } else {
            color = getResources().getColor(android.R.color.darker_gray);
        }
        tvWeather.setTextColor(color);
    }

    private void setSoilStatusColor(String status) {
        int color;
        // Match backend soil_status() function values
        if (status.equals("Healthy Soil")) {
            color = getResources().getColor(android.R.color.holo_green_dark);
        } else if (status.equals("Soil Data Unavailable")) {
            color = getResources().getColor(android.R.color.darker_gray);
        } else if (status.equals("Dry Soil")) {
            color = getResources().getColor(android.R.color.holo_orange_dark);
        } else {
            color = getResources().getColor(android.R.color.darker_gray);
        }
        tvSoilStatus.setTextColor(color);
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnCalculate.setEnabled(false);
            cvResults.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            btnCalculate.setEnabled(true);
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel any pending requests when activity is destroyed
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}