from sqlalchemy import Column, Integer, Float, String
from database.db import Base

class Evaluation(Base):

    __tablename__ = "evaluations"

    id = Column(Integer, primary_key=True)

    lat = Column(Float)
    lon = Column(Float)

    ndvi = Column(Float)

    rainfall = Column(Float)

    yield_est = Column(Float)

    revenue = Column(Float)

    loan_limit = Column(Float)

    score = Column(Float)

    decision = Column(String)