use `auction-db`;

INSERT INTO roles (name, description)
VALUES ('SELLER', 'A user that can set products for auction'),
       ('BIDDER', 'A user that can bid on an auction'),
       ('GUEST', 'A user that has no other right but, to view/search for auctions'),
       ('ADMIN', 'A user that has administrative permission but not necessarily bidder\'s or seller\'s')