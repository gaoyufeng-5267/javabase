--INSERT INTO mst_withdrawal_fee (id, currency, withdrawal_type, minimum_quantity, maximum_quantity, fee, transaction_fee, deleted, create_user, create_time, update_user, update_time, version) VALUES ('aaefef96-54de-4337-ac51-57406af6e71f', 'BTC', 'HOT', 0, 10, 0.001, 0.0001, false, 'system', now(), 'system', now(), 0);
--INSERT INTO mst_withdrawal_fee (id, currency, withdrawal_type, minimum_quantity, maximum_quantity, fee, transaction_fee, deleted, create_user, create_time, update_user, update_time, version) VALUES ('0fa0eed6-8b34-4bc8-957c-cc760e9b0c83', 'BTC', 'COLD', 10, 10000, 1, 1, false, 'system', now(), 'system', now(), 0);

-- uuid option
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- gcrypto option
-- CREATE EXTENSION pgcrypto;
-------------------------------------------------
-- mst_system_parameter
-------------------------------------------------
-- common parameter
INSERT INTO public.mst_system_parameter(id, param_key, param_value, deleted, create_user, create_time, update_user, update_time, version) VALUES ('81030e83-5bbd-49c7-a024-b1796d6b1008', 'SYS_UNCREATED_SIGNUP', '1', false, 'system', now(), 'system', now(), 0);


-------------------------------------------------
-- tbl_staff (administrator user create)
-------------------------------------------------
INSERT INTO public.tbl_staff(id, staff_id, name, password, email, login_failed, locked, unlocked_date, default_language, role_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('d407382f-649c-4288-97d0-3d1772ac4593', 'administrator', 'administrator', '$2a$10$AzZKUKh/ej8Y9nHxv8kNsuDXgre./jsqlQLo27TVt6ZUUcc13RAYK', 'sample@axcel-mode.co.jp', 0, false, null, 'en_us', 'administrator', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('0bc306f2-d20e-45b6-837d-ee217e31d6b2', 'administrator', 'ManagerWebReferenceStaff', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('0ef96736-6d90-4308-8b03-0246151e2b32', 'administrator', 'ManagerWebReferenceUser', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('f9ce88c3-87ac-4f1e-8a5b-0980bc3b7377', 'administrator', 'ManagerWebReferenceWithdrawalHistory', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('a8bab589-7e4f-41bc-bd6f-8d875f581e6b', 'administrator', 'ManagerWebReferenceTransfer', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('c972aeec-4f16-49ee-91a6-00ed11b4f0b0', 'administrator', 'ManagerWebReferenceWithDraw', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('14f13e93-0a69-4e40-954e-f9922e81388c', 'administrator', 'ManagerWebUpdateRole', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('d9d8c036-1fba-40b6-8aed-9c1804cb22e6', 'administrator', 'ManagerWebReferenceUserStrategy', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('1bec3e4c-9ded-4404-8560-0a192569f398', 'administrator', 'ManagerWebNotifyAll', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('b29fc2c0-2a21-4fdf-ad10-e5986a2a4277', 'administrator', 'ManagerWebRegisterStaff', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('5365d47b-5a03-4032-82b2-1f7c2d9bc356', 'administrator', 'ManagerWebReferenceUserDetail', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('8e4880f0-7fcb-49aa-b5bc-f9aa1c83c787', 'administrator', 'ManagerWebDeleteRole', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('9b6c92e0-2f60-4cad-b6b7-59e5f2900ba4', 'administrator', 'ManagerWebDeleteStaff', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('3bb9470e-539f-4de1-b8ab-ac2dc9e823af', 'administrator', 'ManagerWebReferenceTradeHistory', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('779a66a2-62e2-4cc9-9c1f-050bb2f93ea8', 'administrator', 'ManagerWebReferenceDepositHistory', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('10b7be47-c291-4387-930f-115024364fcf', 'administrator', 'ManagerWebReferencePrivateNotification', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('eaaa9f69-05b7-42c8-94df-665f6db4a7db', 'administrator', 'ManagerWebUpdateStaff', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('4c3530df-15d2-4065-89da-db9efe573078', 'administrator', 'ManagerWebReferencePrivateTemplateNotification', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('81cad8c2-b7f5-4f24-90a9-8187f9e41831', 'administrator', 'ManagerWebRegisterRole', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('4c8dc69c-8e85-4bdd-8860-9e679e93c520', 'administrator', 'ManagerWebReferenceRole', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('cf861364-ae6c-4018-adab-84ca6f4662b2', 'administrator', 'ManagerWebSystemSetting', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('ee080213-4402-469f-b78e-6a8b76d08042', 'administrator', 'ManagerWebReferenceUserBasic', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('cd00808b-1c9f-4c9c-af8c-3cfc80e87de9', 'administrator', 'ManagerWebInboundCoinControl', false, 'sample1', now(), 'sample2', now(), 1);
INSERT INTO public.tbl_staff_role(id, role_id, authority_id, deleted, create_user, create_time, update_user, update_time, version) VALUES ('5f461ca0-78bf-4002-bd38-680825aad0bb', 'administrator', 'ManagerWebUpdateUserStatus', false, 'sample1', now(), 'sample2', now(), 1);
--INSERT INTO public.tbl_products(id, distributor_id, brand_name, product_name, general_price, price_amount_1, price_amount_2to10, price_amount_11to20, price_amount_21to30, price_amount_31to40, price_amount_41to50, price_amount_51to60, price_amount_61to70, price_amount_71to80, price_amount_81to90, price_amount_91to100, image_url_1, image_url_2, image_url_3, image_url_4, image_url_5, product_lp_url, youtube_url, discription, product_number, introduction, announce_date, start_date, finish_date, delivery_schedule, sub_category_id, product_total_amount, product_sold_total_amount, transporter_id, tax, current_price, limit_item, limit_quantity, off_shelf, registration_date, settlement_date, population, population_modify_datetime) VALUES
--('1', '1001', '株式会社東亜産業', 'UVクリアエージ', 39800, 31840, 31840, 27860, 19900, 19900, 19900, 19900, 19900, 19900, 19900, 19900, 'fa5f82f1984c460.jpeg', '6e5f82f1984fe4e.jpeg', 'ae5f82f19851f7e.jpeg', '895f82f19853704.jpeg', '8d5f82f198544a9.jpeg', '', '', '仕様\r\n•	販売名:UV クリアエージ\r\n•	消費電力:冷風31W 暖風1,700W\r\n•	サイズ:(約)幅265mm/160mm×高855mm\r\n•	風量:15m3/min\r\n•	材質:ABS,PC,PA\r\n•	質量:3.5kg\r\n•	適用床面積(目安):~40m2(~24畳)\r\n•	電源:100V~50/60Hz\r\n•	電源コード長さ:1.8m\r\nご使用上の注意\r\n•	※この', '', '', '2020-10-11 00:00:00', '2020-10-12 00:00:00', '2020-10-13 23:59:59', '2020-10-31', 0, 50, 0, 1, 10, 0, false, 0, false, '2020-10-11', '2020-10-14 16:10:01', false, '2020-10-14 16:10:01');
