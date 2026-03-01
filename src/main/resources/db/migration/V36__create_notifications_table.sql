-- Create sequence
CREATE SEQUENCE notifications_id_seq;

CREATE TABLE notifications (
    id BIGINT PRIMARY KEY DEFAULT nextval('notifications_id_seq'),
    user_id INTEGER NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for fast user search
CREATE INDEX idx_notifications_user_id ON notifications(user_id);

-- Set sequence ownership
ALTER SEQUENCE notifications_id_seq OWNED BY notifications.id;

