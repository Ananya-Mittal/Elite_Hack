from fastapi import APIRouter
from auth.auth_utils import create_token

router = APIRouter()

@router.post("/login")
def login(username: str, password: str):

    if username == "admin" and password == "admin":

        token = create_token(username)

        return {"access_token": token}

    return {"error": "Invalid credentials"}