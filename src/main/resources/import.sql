use `auction-db`;

INSERT INTO roles (name, description)
VALUES ('SELLER', 'A user that can set products for auction'),
       ('BIDDER', 'A user that can bid on an auction'),
       ('GUEST', 'A user that has no other right but, to view/search for auctions'),
       ('ADMIN', 'A user that has administrative permission but not necessarily bidder\'s or seller\'s');

INSERT INTO categories (id, name, description)
VALUES (1, 'Electronics',
        'Bid on the latest gadgets, from high-end smartphones and laptops to gaming consoles and audio equipment.'),
       (2, 'Books & Media',
        'Discover rare first editions, classic films, vintage vinyl, and timeless video games to complete your collection.'),
       (3, 'Home & Garden',
        'Furnish your space with unique decor, find essential tools, and bid on stylish furniture for every room.'),
       (4, 'Fashion & Apparel',
        'Find your next statement piece, from designer handbags and vintage apparel to brand-name shoes and accessories.'),
       (5, 'Collectibles & Art',
        'Bid on fine art, rare antiques, pop culture memorabilia, and unique items for the passionate collector.');

-- Mysql LOAD_FILE function does not work
INSERT INTO users (id, created, username, password, name, surname, email, phone, afm, bidder_rating, seller_rating,
                   location, country, avatar)
VALUES (1, now(), 'admin', 'pass', 'Administrator', 'System',
        'admin@gmail.com', '1111111111', '222222222', null, null,
        'New York', 'USA', LOAD_FILE('admin_avatar.png')),
       (2, now(), 'seller', 'pass', 'Seller', 'Seller Junior',
        'seller@gmail.com', '1111111111', '222222222', null, 7,
        'Athens', 'GRE', LOAD_FILE('seller_avatar.png')),
       (3, now(), 'bidder', 'pass', 'Bidder', 'Bidder Junior',
        'bidder@gmail.com', '1111111111', '222222222', 5, null,
        'Cairo', 'EGY', LOAD_FILE('bidder_avatar.png'));

INSERT INTO user_roles (user_id, role_name)
VALUES (1, 'ADMIN'),
       (2, 'SELLER'),
       (3, 'BIDDER');

INSERT INTO auctions (id, title, created, end_date, first_bid, number_of_bids, seller_id, category_id)
VALUES (1, 'auction 1', NOW(), '2025-09-11T14:30:00', 100, 3, 1, 1),
       (2, 'auction 2', NOW(), '2025-09-12T14:30:00', 200, 3, 1, 2),
       (3, 'auction 3', NOW(), '2025-09-13T14:30:00', 30.65, 3, 1, 3),
       (4, 'auction 4', NOW(), '2025-09-14T14:30:00', 400, 3, 1, 4),
       (5, 'auction 5', NOW(), '2025-09-15T14:30:00', 10.5, 3, 1, 4),
       (6, 'auction 6', NOW(), '2025-09-16T14:30:00', 10.5, 3, 1, 5),
       (7, 'auction 7', NOW(), '2025-09-17T14:30:00', 10.5, 3, 1, 5);
