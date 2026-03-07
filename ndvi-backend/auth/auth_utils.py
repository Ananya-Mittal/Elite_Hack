from jose import jwt
from datetime import datetime, timedelta
from config import SECRET_KEY, ALGORITHM


def create_token(username):

    expire = datetime.utcnow() + timedelta(hours=4)

    payload = {
        "sub": username,
        "exp": expire
    }

    token = jwt.encode(payload, SECRET_KEY, algorithm=ALGORITHM)

    return token