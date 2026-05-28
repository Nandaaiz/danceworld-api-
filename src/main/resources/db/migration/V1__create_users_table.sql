CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL CHECK (user_type IN ('STUDENT', 'TEACHER')),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP
);