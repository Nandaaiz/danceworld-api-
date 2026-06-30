-- Adiciona links sociais na tabela profiles
ALTER TABLE profiles
    ADD COLUMN instagram_url VARCHAR(500),
    ADD COLUMN youtube_url VARCHAR(500),
    ADD COLUMN spotify_url VARCHAR(500),
    ADD COLUMN website_url VARCHAR(500);

-- Cria tabela de eventos
CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    event_type VARCHAR(20) NOT NULL CHECK (event_type IN ('FESTIVAL', 'PARTY', 'SHOW')),
    city VARCHAR(100),
    country VARCHAR(100),
    event_date TIMESTAMP,
    is_free BOOLEAN DEFAULT false,
    price DECIMAL(10,2),
    currency VARCHAR(10) DEFAULT 'BRL',
    ticket_url VARCHAR(500),
    created_by UUID NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP
);