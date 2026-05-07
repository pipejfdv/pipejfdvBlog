-- Prueba la consulta exacta que está causando el problema
SELECT c.id, c.children_id, c.category_of_game 
FROM child_progres c 
WHERE c.children_id = 'TU_CHILD_ID' AND c.category_of_game = 'TU_CATEGORY_ID';
