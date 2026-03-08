# Elite_Hack

## Pitch Video
https://shorturl.at/iUucl
## Demo Video
https://shorturl.at/8MNGM

# Varchas  
AI-Powered Farmer Credit Scoring using Satellite Data  

## Overview  

Varchas is an AI-driven agricultural fintech solution that helps financial institutions assess a farmer’s creditworthiness using satellite imagery, crop health analysis, and environmental data instead of traditional financial history.  

Small and marginal farmers often find it hard to get loans because they lack formal credit records. Varchas addresses this issue by looking at farm productivity indicators such as crop health, rainfall patterns, and yield estimates to create a data-driven credit score.  

The system allows for quicker, fairer, and more inclusive agricultural lending.

## Problem Statement

Many farmers lack access to formal credit because they do not have a financial credit history. Banks depend heavily on paperwork and collateral. Loan approval processes are slow and risky. This results in financial exclusion and delayed agricultural growth.

## Our Solution

Varchas introduces an AI-based credit scoring system that assesses a farmer's eligibility for loans using remote sensing and machine learning.

The system analyzes:

- Satellite-based crop health
- Rainfall and weather data
- Estimated crop yield
- Soil indicators

Using these inputs, the system generates a credit score (0–100) which helps lenders make decisions based on data.

## Key Features

- AI-based credit scoring
- Satellite image crop health analysis
- NDVI vegetation detection
- Weather-based risk analysis
- Crop yield prediction
- Automated loan decision system

## How It Works
Step 1 – Location Input

The user enters farm coordinates (latitude and longitude) in the mobile application.

Step 2 – Satellite Image Processing

The backend retrieves satellite imagery of the farm area.

Step 3 – NDVI Calculation

Crop health is determined using the Normalized Difference Vegetation Index (NDVI).

NDVI Formula:

NDVI = (NIR - RED) / (NIR + RED)

Where:

NIR = Near Infrared band

RED = Red band

NDVI values indicate crop health:

NDVI Value	Crop Health
0.6 – 0.9	Healthy vegetation
0.4 – 0.6	Moderate vegetation
0.2 – 0.4	Poor vegetation
Step 4 – Environmental Data Collection

Weather APIs provide:

Rainfall data

Climate conditions

These factors influence crop productivity.

Step 5 – Yield Estimation

Crop yield is estimated using NDVI and weather parameters.

Step 6 – Machine Learning Prediction

A trained machine learning model predicts the credit score based on:

features = [NDVI, Rainfall, Soil Value, Yield Estimate]
Step 7 – Credit Decision

- Based on the predicted score:

Credit Score	      Decision
75 – 100	         Loan Approved
50 – 74	           Manual Review
0 – 49	           Loan Rejected

### System Architecture

Mobile App → Backend API → Satellite Data → Image Processing → ML Model → Credit Score

## Tech Stack

### Frontend

Android Studio

Java / Kotlin

XML UI

### Backend

Python

Flask API

AI / ML

Scikit-learn

NDVI-based vegetation analysis

APIs

Satellite imagery APIs

Weather data APIs

Geolocation services

### Database

Prototype uses:

Local storage / JSON

Future integration:

Firebase

PostgreSQL

## Real World Impact

Varchas can significantly improve agricultural finance by:

Enabling credit access for farmers without financial records

Reducing loan approval time

Helping banks assess agricultural risk

Promoting data-driven lending

## Team

Developed by Ananya Mittal and Pari Bhoomi
