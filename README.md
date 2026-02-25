1️⃣ Start Docker Desktop (VERY IMPORTANT)

**✔ Open Docker Desktop**

**✔ Wait until it says Docker is running**

**✔ Make sure no errors**



**inside project folder:**

docker ps 

or

**docker-compose up -d**



1️⃣ Start Docker

2️⃣ Ensure MySQL container is running

3️⃣ Open IDE

4️⃣ Run Spring Boot

5️⃣ Copy security password

6️⃣ Start testing controllers in Postman


---------
temp login steps 
1) PasswordGenerator will generate >>BCrypt Password
2) register a user manually in sql  >
INSERT INTO users (name, email, password, created_at)
VALUES (
    'Admin User',
    'admin@example.com',
    '$2a$10$XJk9q...',  -- paste your generated hash here
    NOW()
);

3) Test Login >> POST http://localhost:8080/api/auth/login
pass below Body:

{
  "email": "admin@example.com",
  "password": "password123"
}

>>>>It will generate a JWT Token : 
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}

5) Test the Endpoint :
GET /api/incidents >> without token it will give 401 or 403 Errors
with Bearer Token : Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
will give the result


