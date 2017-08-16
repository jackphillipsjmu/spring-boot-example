-- On startup of application insert records into in memory Todo table
-- Notice you may either specify true/false or 'true'/'false' for booleans
INSERT INTO todo (id, is_complete, message) VALUES
  (1, false, 'Incomplete todo (Autgenerated Example)'),
  (2, 'true', 'Complete todo (Autgenerated Example)');