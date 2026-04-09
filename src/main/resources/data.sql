-- Insert Users
INSERT INTO USER (user_name, password, type)
VALUES
    ('john_doe', '', 'REGULAR'),
    ('chuck_norris', '', 'ADMIN')
ON DUPLICATE KEY UPDATE user_name = VALUES(user_name);

-- Insert Books
INSERT INTO BOOK (title, category)
VALUES 
    ("Don Quijote de la Mancha", "NOVEL"),
    ("Cien Años de Soledad", "NOVEL"),
    ("El Principito", "STORY"),
    ("La Casa de los Espíritus", "NOVEL"),
    ("Sapiens: De animales a dioses", "SCIENCE"),
    ("Historia de Roma", "HISTORY"),
    ("El Arte de la Guerra", "OTHER"),
    ("Cuentos de la Selva", "STORY"),
    ("La Guerra y la Paz", "NOVEL"),
    ("El amor en los tiempos del cólera", "NOVEL"),
    ("Rayuela", "NOVEL"),
    ("Pedro Páramo", "NOVEL"),
    ("La ciudad y los perros", "NOVEL"),
    ("Conversación en La Catedral", "NOVEL"),
    ("La tía Julia y el escribidor", "NOVEL"),
    ("El alquimista", "STORY"),
    ("La historia interminable", "STORY"),
    ("El caballero de la armadura oxidada", "STORY"),
    ("Breve historia del tiempo", "SCIENCE"),
    ("El universo en una cáscara de nuez", "SCIENCE"),
    ("Cosmos", "SCIENCE"),
    ("La realidad no es lo que parece", "SCIENCE"),
    ("Homo Deus", "HISTORY"),
    ("21 lecciones para el siglo XXI", "HISTORY"),
    ("La historia de la humanidad", "HISTORY")
ON DUPLICATE KEY UPDATE title = VALUES(title);
    
    
    