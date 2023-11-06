TRUNCATE user_role, account, telegram_user, admin_account_telegram_user, admin_account,
general_config, config, wifi, devices, connection_internet_type, wifi_mode, connection_device_type;

INSERT INTO connection_internet_type (connection_internet_id, connection_internet_name)
VALUES (1, 'Статический IP-адрес'), (2, 'Динамический IP-адрес');

INSERT INTO wifi_mode (mode_id, mode_name)
VALUES (1, '802.11ax'), (2, '802.11b/g/n'), (3, '802.11b/g/n/ax');

INSERT INTO connection_device_type (connection_device_id, connection_device_name)
VALUES (1, 'Проводной'), (2, 'Беспроводной');

INSERT INTO devices (device_id, device_name, ip_address, mac_address, connection_device_id)
VALUES
(1, 'Asus-14X-M1403QA-LY113', '192.168.0.50', 'CC-09-C8-01-23-EE', 1),
(2, 'A52-Galaxy', '192.168.0.202', 'D8-5E-D3-20-69-A0', 2),
(3, 'HUAWEI-P60-Pro', '192.168.0.235', 'EA-64-43-46-F9-26', 2),
(4, 'LAPTOP-0S3552SH', '192.168.0.181', '7A-B0-2A-06-7D-B8', 1),
(5, 'Redmi-Note-10S', '192.168.0.5', 'C0-E4-34-25-02-79', 2),
(6, 'Redmi8', '192.168.0.140', '9C-C5-02-B1-3F-A4', 2),
(7, 'android-4ab4745c4eb23cfa', '192.168.0.230', '45-76-8D-AB-10-EF', 2),
(8, 'AntonPC', '192.168.0.224', 'F1-2B-3C-4D-5E-6F', 1),
(9, 'Iphone10', '192.168.0.107', '2C-58-41-9A-76-F3', 2),
(10, 'Galaxy-A70', '192.168.0.10', '8A-9B-C7-F4-D6-02', 2);

INSERT INTO config (config_id, ip, mask, gate, connection_internet_id)
VALUES (1, null, null, null, 2);

INSERT INTO wifi (wifi_id, ssid, password, mode_id)
VALUES (1, null, null, 1);

INSERT INTO account (account_id, account_number, sum)
VALUES
(1, '5015697192', 370396.13),
(2, '4762552586', 263494.76),
(3, '5949560421', 856609.26);

INSERT INTO admin_account (admin_account_id, login, password, active, account_id)
VALUES
(1, 'admin1', '$2a$10$17NEW8k6jFPfRKWdb4DUfONpZiN9mFxEqht4xlktNF3.6cDI6Fv/S', false, 1),
(2, 'admin2', '$2a$10$fdCTG6G6uHzlgGITxUW7KuE8jpFog/.ZpcbHcipwlFGLy5s4qaptS', false, 2),
(3, 'admin3', '$2a$10$hnIEcAaik1wigWcuv2VNPecnpHbqeyGOCxLOwz1kVcvSzSLMNygBW', false, 3);

INSERT INTO general_config (general_config_id, admin_account_id, config_id, wifi_id)
VALUES (1, 1, 1, 1);

INSERT INTO telegram_user(telegram_user_id, telegram_user_name)
VALUES (284030265, 'test1');

INSERT INTO admin_account_telegram_user(telegram_user_id, admin_account_id)
VALUES (1, 284030265);