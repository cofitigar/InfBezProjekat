INSERT INTO users(email,password,certificate,active)VALUES('usera','$2a$06$euSKYRGaM8E8U6S1eZRs5uLYG6Z4EpQvxhKQrVo3N.eAG6edxSJ6S',NULL,true)
INSERT INTO users(email,password,certificate,active)VALUES('userb','$2a$06$euSKYRGaM8E8U6S1eZRs5uLYG6Z4EpQvxhKQrVo3N.eAG6edxSJ6S',NULL,true)
INSERT INTO users(email,password,certificate,active)VALUES('userc','$2a$06$euSKYRGaM8E8U6S1eZRs5uLYG6Z4EpQvxhKQrVo3N.eAG6edxSJ6S',NULL,false)
INSERT INTO users(email,password,certificate,active)VALUES('userd','$2a$06$euSKYRGaM8E8U6S1eZRs5uLYG6Z4EpQvxhKQrVo3N.eAG6edxSJ6S',NULL,false)
INSERT INTO users(email,password,certificate,active)VALUES('usere','$2a$06$euSKYRGaM8E8U6S1eZRs5uLYG6Z4EpQvxhKQrVo3N.eAG6edxSJ6S',NULL,false)



INSERT INTO authority(name)VALUES('ADMIN')
INSERT INTO authority(name)VALUES('REGULAR')

INSERT INTO user_authority(user_id,authority_id)VALUES(1,1)
INSERT INTO user_authority(user_id,authority_id)VALUES(1,2)
INSERT INTO user_authority(user_id,authority_id)VALUES(2,2)
INSERT INTO user_authority(user_id,authority_id)VALUES(3,2)
INSERT INTO user_authority(user_id,authority_id)VALUES(4,2)
INSERT INTO user_authority(user_id,authority_id)VALUES(5,2)


