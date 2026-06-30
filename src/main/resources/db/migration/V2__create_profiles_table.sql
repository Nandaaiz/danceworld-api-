ALTER TABLE users DROP CONSTRAINT IF EXISTS users_user_type_check;

UPDATE users SET user_type = 'DANCER' WHERE user_type = 'STUDENT';

ALTER TABLE users ADD CONSTRAINT users_user_type_check 
    CHECK (user_type IN ('DANCER', 'TEACHER', 'ARTIST'));

CREATE TABLE profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    display_name VARCHAR(100),
    bio TEXT,
    city VARCHAR(100),
    country VARCHAR(100),
    profile_photo_url VARCHAR(500),
    dance_styles VARCHAR(255),
    artist_type VARCHAR(20) CHECK (artist_type IN ('DJ', 'BAND', 'SOLO')),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP
);