-- Ver duplicados
SELECT children_id, category_of_game, COUNT(*) as count 
FROM child_progres 
GROUP BY children_id, category_of_game 
HAVING COUNT(*) > 1;

-- Eliminar duplicados (mantener el más reciente por ID)
DELETE FROM child_progres 
WHERE id NOT IN (
    SELECT MAX(id) 
    FROM child_progres 
    GROUP BY children_id, category_of_game
);
