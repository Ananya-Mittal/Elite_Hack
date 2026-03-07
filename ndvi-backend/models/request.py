from pydantic import BaseModel


class CreditRequest(BaseModel):
    lat: float
    lon: float
    crop: str = "wheat"
