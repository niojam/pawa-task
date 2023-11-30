INSERT INTO task.task (id, title, description, priority, deadline, created_at, created_by, modified_at, modified_by)
VALUES (1, 'Task 1', 'Description for Task 1', 'HIGH', '2023-12-01 08:00:00', '2023-11-29 10:00:00',
        UUID_TO_BIN('b54dc87a-00f2-4603-a12e-4f4e2e3fda51'), '2023-11-29 10:00:00', UUID_TO_BIN('b54dc87a-00f2-4603-a12e-4f4e2e3fda51'));

INSERT INTO task.comment (id, task_id, content, created_at, created_by, modified_at, modified_by)
VALUES (1, 1, 'First comment on Task 1', '2023-11-29 10:05:00', UUID_TO_BIN('b54dc87a-00f2-4603-a12e-4f4e2e3fda51'), '2023-11-29 10:05:00',
        UUID_TO_BIN('b54dc87a-00f2-4603-a12e-4f4e2e3fda51'));
