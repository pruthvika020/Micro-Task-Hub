from fastapi import FastAPI, APIRouter, Header
from fastapi.middleware.cors import CORSMiddleware
import httpx

from schema import SigninSchema, SignupSchema

app = FastAPI()
router = APIRouter()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def welcome():
    return "Welcome to the API Gateway!"

@app.get("/test")
def test():
    return "This is a test endpoint!"

# welcome()

@router.post("/authservice/signin")
async def signin(user: SigninSchema):
    print(user)
    async with httpx.AsyncClient() as client:
        response = await client.post("http://localhost:8001/authservice/signin", json=user.model_dump())
    return response.json()

@router.post("/authservice/signup")
async def signup(user: SignupSchema):
    print(user)
    async with httpx.AsyncClient() as client:
        response = await client.post("http://localhost:8001/authservice/signup", json=user.model_dump())
    return response.json()

@router.get("/authservice/rbac")
async def uinfo(Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get("http://localhost:8001/authservice/uinfo", headers={"Token": Token})
    return response.json()

@router.get("/authservice/profile")
async def profile(Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get("http://localhost:8001/authservice/profile", headers={"Token": Token})
    return response.json()

@router.get("/authservice/getallusers/{page}/{limit}")
async def getallusers(page: int, limit: int, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get(f"http://localhost:8001/authservice/getallusers/{page}/{limit}", headers={"Token": Token})
    return response.json()

@router.get("/authservice/getuser/{id}")
async def getuser(id: int, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get(f"http://localhost:8001/authservice/getuser/{id}", headers={"Token": Token})
    return response.json()

@router.post("/authservice/saveuser")
async def saveuser(user: SignupSchema, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.post("http://localhost:8001/authservice/saveuser", json=user.model_dump(), headers={"Token": Token})
    return response.json()

@router.put("/authservice/updateuser/{id}")
async def updateuser(id: int, user: SignupSchema, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.put(f"http://localhost:8001/authservice/updateuser/{id}", json=user.model_dump(), headers={"Token": Token})
    return response.json()

@router.delete("/authservice/deleteuser/{id}")
async def deleteuser(id: int, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.delete(f"http://localhost:8001/authservice/deleteuser/{id}", headers={"Token": Token})
    return response.json()

@router.get("/authservice/searchuser/{key}")
async def searchUser(key: str):
    async with httpx.AsyncClient() as client:
        response= await client.get(f"http://localhost:8001/authservice/searchuser/{key}")
    return response.json()

@router.post("/taskservice/createtask")
async def createtask(data: dict, Token: str = Header(...)):
    print(Token)
    print(data)
    async with httpx.AsyncClient() as client:
        response = await client.post("http://localhost:8002/tasks/createtask", json=data, headers={"Token": Token})
        print(response)
    return response.json()

@router.get("/taskservice/getalltasks/{PAGE}/{SIZE}")
async def getalltasks(PAGE: int, SIZE: int, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get(f"http://localhost:8002/tasks/getalltasks/{PAGE}/{SIZE}", headers={"Token": Token})
    return response.json()

@router.delete("/taskservice/deletetask/{ID}")
async def deletetask(ID: str, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.delete(f"http://localhost:8002/tasks/deletetask/{ID}", headers={"Token": Token})
    return response.json()

@router.get("/taskservice/gettask/{ID}")
async def gettask(ID: str, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get(f"http://localhost:8002/tasks/gettask/{ID}", headers={"Token": Token})
    return response.json()

@router.put("/taskservice/updatetask/{ID}")
async def updatetask(ID: str, data: dict, Token: str = Header(...)):
    print(Token)
    print(data)
    async with httpx.AsyncClient() as client:
        response = await client.put(f"http://localhost:8002/tasks/updatetask/{ID}", json=data, headers={"Token": Token})
    return response.json()

@router.get("/taskservice/vectorsearch/{QUERY}")
async def vectorsearch(QUERY: str, Token: str = Header(...)):
    print(Token)
    async with httpx.AsyncClient() as client:
        response = await client.get(f"http://localhost:8002/tasks/vectorsearch/{QUERY}", headers={"Token": Token})
    return response.json()

app.include_router(router)