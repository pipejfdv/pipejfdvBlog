-- Buscar exactamente qué registros coinciden
-- Reemplaza los UUIDs con los valores reales que estás usando
SELECT 
    c.id,
    c.children_id,
    c.category_of_game,
    c.xp,
    c.level,
    c.attempts_daily
FROM child_progres c 
WHERE c.children_id = 'CHILD_ID_AQUI' 
  AND c.category_of_game = 'CATEGORY_ID_AQUI';
