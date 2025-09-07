use `auction-db`;

INSERT INTO roles (name, description)
VALUES ('SELLER', 'A user that can set products for auction'),
       ('BIDDER', 'A user that can bid on an auction'),
       ('GUEST', 'A user that has no other right but, to view/search for auctions'),
       ('ADMIN', 'A user that has administrative permission but not necessarily bidder\'s or seller\'s');

INSERT INTO categories (id, name, description)
VALUES (1,  'Electronics', 'Bid on the latest gadgets, from high-end smartphones and laptops to gaming consoles and audio equipment.'),
       (2,  'Books & Media', 'Discover rare first editions, classic films, vintage vinyl, and timeless video games to complete your collection.'),
       (3,  'Home & Garden', 'Furnish your space with unique decor, find essential tools, and bid on stylish furniture for every room.'),
       (4,  'Fashion & Apparel', 'Find your next statement piece, from designer handbags and vintage apparel to brand-name shoes and accessories.'),
       (5,  'Collectibles & Art', 'Bid on fine art, rare antiques, pop culture memorabilia, and unique items for the passionate collector.');