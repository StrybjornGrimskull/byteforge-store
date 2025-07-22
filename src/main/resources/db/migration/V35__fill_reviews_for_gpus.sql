-- Reviews for NVIDIA GeForce RTX 4090 (product_id 1)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(1, 1, 'John', 5, 'Absolutely amazing card! Handles 4K gaming at max settings with ease. The cooling is exceptional even under heavy load.', TRUE),
(1, 2, 'Mike', 4, 'Incredibly powerful GPU, but requires a massive power supply. Can get a bit noisy at full load.', TRUE),
(1, 3, 'Sarah', 5, 'The best graphics card on the market right now. DLSS 3.5 is like magic - doubles FPS without quality loss.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4080 Super (product_id 2)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(2, 3, 'Sarah', 4, 'Great upgrade from my 3080. Runs cool and quiet most of the time.', TRUE),
(2, 1, 'John', 5, 'Perfect balance of price and performance. Handles 4K gaming beautifully.', TRUE),
(2, 2, 'Mike', 3, 'Good card but I expected more performance uplift over regular 4080.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4080 (product_id 3)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(3, 2, 'Mike', 4, 'Solid 4K performance. DLSS 3 works wonders in supported games.', TRUE),
(3, 3, 'Sarah', 3, 'Good card but overpriced compared to AMD alternatives.', TRUE),
(3, 1, 'John', 5, 'My editing workstation loves this GPU. Renders videos twice as fast as my old card.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4070 Ti Super (product_id 4)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(4, 1, 'John', 5, 'Perfect 1440p card. Handles everything I throw at it with ease.', TRUE),
(4, 2, 'Mike', 4, 'Great value in the current market. The extra VRAM helps in modern games.', TRUE),
(4, 3, 'Sarah', 3, 'Decent performance but runs hotter than I expected.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4070 Ti (product_id 5)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(5, 3, 'Sarah', 4, 'Excellent for 1440p gaming. Compact size fits my small case.', TRUE),
(5, 1, 'John', 3, 'Good performance but the 12GB VRAM might limit future games.', TRUE),
(5, 2, 'Mike', 5, 'Perfect for my streaming setup. AV1 encoding is a game changer.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4070 Super (product_id 6)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(6, 2, 'Mike', 5, 'Best price-to-performance ratio in NVIDIA lineup right now.', TRUE),
(6, 3, 'Sarah', 4, 'Great upgrade from my 2070 Super. Noticeable improvement in all games.', TRUE),
(6, 1, 'John', 5, 'Runs everything at 1440p ultra with high FPS. Very impressed.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4060 Ti 16GB (product_id 7)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(7, 1, 'John', 4, 'The extra VRAM makes this version worth it for content creation.', TRUE),
(7, 2, 'Mike', 3, 'Good for 1080p gaming but struggles at 1440p in newer titles.', TRUE),
(7, 3, 'Sarah', 5, 'Perfect for my Blender work. The 16GB helps with complex scenes.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4060 Ti 8GB (product_id 8)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(8, 3, 'Sarah', 4, 'Great 1080p card. Handles esports titles at very high FPS.', TRUE),
(8, 1, 'John', 3, 'Good but the 8GB VRAM is already limiting in some games.', TRUE),
(8, 2, 'Mike', 5, 'Perfect upgrade from my GTX 1060. Huge performance jump.', TRUE);

-- Reviews for NVIDIA GeForce RTX 4060 (product_id 9)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(9, 2, 'Mike', 4, 'Great budget option for 1080p gaming. DLSS 3 helps a lot.', TRUE),
(9, 3, 'Sarah', 3, 'Decent performance but AMD offers better value at this price.', TRUE),
(9, 1, 'John', 5, 'Perfect for my son''s first gaming PC. Handles Fortnite great.', TRUE);

-- Reviews for AMD Radeon RX 7900 XTX (product_id 10)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(10, 1, 'John', 5, 'AMD''s best GPU yet. Competes with 4090 in many games for less money.', TRUE),
(10, 2, 'Mike', 4, 'Excellent 4K performance. FSR 3 works surprisingly well.', TRUE),
(10, 3, 'Sarah', 3, 'Runs hot and loud under full load. Cooling could be better.', TRUE);

-- Reviews for AMD Radeon RX 7900 XT (product_id 11)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(11, 3, 'Sarah', 4, 'Great alternative to NVIDIA. Performs close to XTX for less money.', TRUE),
(11, 1, 'John', 3, 'Good but the VRAM bandwidth seems limited compared to XTX.', TRUE),
(11, 2, 'Mike', 5, 'Perfect for my 3440x1440 ultrawide. Handles everything maxed out.', TRUE);

-- Reviews for AMD Radeon RX 7800 XT (product_id 12)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(12, 2, 'Mike', 5, 'Best value high-end GPU right now. Beats 4070 in most games.', TRUE),
(12, 3, 'Sarah', 4, 'Great 1440p card. The 16GB VRAM gives peace of mind.', TRUE),
(12, 1, 'John', 3, 'Good but runs hotter than comparable NVIDIA cards.', TRUE);

-- Reviews for AMD Radeon RX 7700 XT (product_id 13)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(13, 1, 'John', 4, 'Solid mid-range option. Handles 1440p gaming well.', TRUE),
(13, 2, 'Mike', 3, 'Good but the price could be slightly lower for the performance.', TRUE),
(13, 3, 'Sarah', 5, 'Perfect for my needs. Smart Access Memory helps with my Ryzen CPU.', TRUE);

-- Reviews for AMD Radeon RX 7600 XT (product_id 14)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(14, 3, 'Sarah', 4, 'Great 1080p card with future-proof 16GB VRAM.', TRUE),
(14, 1, 'John', 3, 'Good but the memory bus is narrow, limiting performance.', TRUE),
(14, 2, 'Mike', 5, 'Perfect for my son''s gaming PC. Handles esports titles at high FPS.', TRUE);

-- Reviews for AMD Radeon RX 7600 (product_id 15)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(15, 2, 'Mike', 4, 'Great budget option. Perfect for 1080p gaming.', TRUE),
(15, 3, 'Sarah', 3, 'Decent but the 8GB VRAM might limit future games.', TRUE),
(15, 1, 'John', 5, 'Excellent upgrade from integrated graphics. Huge difference.', TRUE);

-- Reviews for ASUS ROG Strix RTX 4090 OC (product_id 16)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(16, 1, 'John', 5, 'The best 4090 model. Amazing cooling and RGB lighting.', TRUE),
(16, 2, 'Mike', 4, 'Massive card but performs incredibly well. Expensive though.', TRUE),
(16, 3, 'Sarah', 5, 'Worth every penny for enthusiasts. The overclock is stable.', TRUE);

-- Reviews for ASUS TUF RTX 4080 Super (product_id 17)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(17, 3, 'Sarah', 4, 'TUF series never disappoints. Excellent build quality.', TRUE),
(17, 1, 'John', 5, 'Military-grade components give me peace of mind.', TRUE),
(17, 2, 'Mike', 3, 'Good but the design is a bit plain compared to ROG models.', TRUE);

-- Reviews for MSI Suprim X RTX 4090 (product_id 18)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(18, 2, 'Mike', 5, 'Premium build quality. The metal backplate looks amazing.', TRUE),
(18, 3, 'Sarah', 4, 'Excellent cooling solution. Stays cool even under heavy load.', TRUE),
(18, 1, 'John', 5, 'The Torx Fan 5.0 technology really makes a difference in noise levels.', TRUE);

-- Reviews for MSI Gaming X Trio RTX 4080 Super (product_id 19)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(19, 1, 'John', 4, 'Great balance between performance and aesthetics.', TRUE),
(19, 2, 'Mike', 5, 'The TRI FROZR 3 cooling is exceptional. Very quiet operation.', TRUE),
(19, 3, 'Sarah', 4, 'Mystic Light RGB looks fantastic in my build.', TRUE);

-- Reviews for Gigabyte AORUS RTX 4090 XTREME (product_id 20)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(20, 3, 'Sarah', 5, 'The LCD Edge View is a cool feature for monitoring stats.', TRUE),
(20, 1, 'John', 4, 'Windforce cooling works great. Alternate spinning fans help with noise.', TRUE),
(20, 2, 'Mike', 5, 'AORUS quality is top notch. Worth the premium for enthusiasts.', TRUE);

-- Reviews for Gigabyte Gaming OC RTX 4070 Super (product_id 21)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(21, 2, 'Mike', 4, 'Great mid-range option with excellent cooling.', TRUE),
(21, 3, 'Sarah', 5, 'The semi-passive fans are silent during light use. Perfect for my quiet build.', TRUE),
(21, 1, 'John', 4, 'Screen cooling heatsink works as advertised. Good temps.', TRUE);

-- Reviews for Sapphire RX 7900 XTX Nitro+ (product_id 22)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(22, 1, 'John', 5, 'Sapphire''s best AMD card. The Tri-X cooling is exceptional.', TRUE),
(22, 2, 'Mike', 4, 'Great alternative to NVIDIA flagships. Performs very well.', TRUE),
(22, 3, 'Sarah', 5, 'The dual BIOS switch is a nice touch for different use cases.', TRUE);

-- Reviews for PowerColor RX 7800 XT Red Devil (product_id 23)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(23, 3, 'Sarah', 4, 'Aggressive looks match the performance. Great card.', TRUE),
(23, 1, 'John', 5, 'The eight heat pipes keep this card surprisingly cool.', TRUE),
(23, 2, 'Mike', 4, 'Red Devil series continues to impress. Solid build quality.', TRUE);

-- Reviews for Intel Arc A580 (product_id 24)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(24, 2, 'Mike', 3, 'Decent budget option but drivers still need improvement.', TRUE),
(24, 3, 'Sarah', 4, 'Great value for 1080p gaming. AV1 support is a plus.', TRUE),
(24, 1, 'John', 2, 'Driver issues in older games. Better for newer titles.', TRUE);

-- Reviews for Intel Arc A380 (product_id 25)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(25, 1, 'John', 3, 'Okay for basic gaming and HTPC use. Drivers are getting better.', TRUE),
(25, 2, 'Mike', 2, 'Struggles with many games. Only recommend for very light gaming.', TRUE),
(25, 3, 'Sarah', 4, 'Perfect for my office PC. Handles multimedia great.', TRUE);

-- Reviews for NVIDIA GeForce RTX 3050 (product_id 26)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(26, 3, 'Sarah', 4, 'Great entry-level card with ray tracing support.', TRUE),
(26, 1, 'John', 3, 'Good for 1080p medium settings but showing its age.', TRUE),
(26, 2, 'Mike', 5, 'Perfect for my kid''s first gaming PC. Handles esports great.', TRUE);

-- Reviews for NVIDIA GeForce GTX 1660 Super (product_id 27)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(27, 2, 'Mike', 4, 'Still a solid 1080p card years after release.', TRUE),
(27, 3, 'Sarah', 5, 'Great value used card for budget builds.', TRUE),
(27, 1, 'John', 3, 'Starting to show its age but still capable.', TRUE);

-- Reviews for AMD Radeon RX 6500 XT (product_id 28)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(28, 1, 'John', 3, 'Okay for very budget builds but 4GB VRAM is limiting.', TRUE),
(28, 2, 'Mike', 2, 'Poor performance without PCIe 4.0. Not recommended for older systems.', TRUE),
(28, 3, 'Sarah', 4, 'Good for what it is - a basic 1080p card for casual gaming.', TRUE);

-- Reviews for AMD Radeon RX 6400 (product_id 29)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(29, 3, 'Sarah', 3, 'Okay for office PCs that occasionally game.', TRUE),
(29, 1, 'John', 2, 'Very limited performance. Only good for basic tasks.', TRUE),
(29, 2, 'Mike', 4, 'Perfect for my HTPC. Doesn''t need external power.', TRUE);

-- Reviews for ASUS Phoenix GTX 1650 (product_id 30)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(30, 2, 'Mike', 4, 'Great for small form factor builds. No power connector needed.', TRUE),
(30, 3, 'Sarah', 3, 'Decent performance for older games. Quiet operation.', TRUE),
(30, 1, 'John', 5, 'Perfect upgrade for old office PCs. Plug and play.', TRUE);

-- Reviews for MSI RX 6500 XT Mech (product_id 31)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(31, 1, 'John', 3, 'Better cooling than reference but still limited by 4GB VRAM.', TRUE),
(31, 2, 'Mike', 2, 'Poor value. Better options available at this price.', TRUE),
(31, 3, 'Sarah', 4, 'Works fine for my needs (light gaming and media).', TRUE);

-- Reviews for Gigabyte RX 6600 Eagle (product_id 32)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(32, 3, 'Sarah', 4, 'Great 1080p card for the price. Handles most games well.', TRUE),
(32, 1, 'John', 5, 'Excellent value. Much better than 6500 XT for slightly more.', TRUE),
(32, 2, 'Mike', 4, 'Windforce cooling works well. Stays relatively quiet.', TRUE);

-- Reviews for NVIDIA GeForce GTX 1050 Ti (product_id 33)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(33, 2, 'Mike', 3, 'Still usable for very basic gaming but showing its age.', TRUE),
(33, 3, 'Sarah', 4, 'Great for upgrading old prebuilts with weak PSUs.', TRUE),
(33, 1, 'John', 2, 'Struggles with modern games. Only for esports titles now.', TRUE);

-- Reviews for AMD Radeon RX 570 (product_id 34)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(34, 1, 'John', 4, 'Still great value used for 1080p medium gaming.', TRUE),
(34, 2, 'Mike', 5, 'Unbeatable price-to-performance in the used market.', TRUE),
(34, 3, 'Sarah', 3, 'Getting old but still capable for basic gaming.', TRUE);

-- Reviews for ASUS GTX 1650 LP BRK (product_id 35)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(35, 3, 'Sarah', 5, 'Perfect for small form factor cases. No power connector needed.', TRUE),
(35, 1, 'John', 4, 'Great for upgrading office PCs with limited space.', TRUE),
(35, 2, 'Mike', 3, 'Performance is limited but good for its size.', TRUE);

-- Reviews for MSI GTX 1050 Ti OC (product_id 36)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(36, 2, 'Mike', 3, 'The overclock helps a bit but this card is getting old.', TRUE),
(36, 3, 'Sarah', 4, 'Still useful for very basic gaming needs.', TRUE),
(36, 1, 'John', 2, 'Struggles with anything modern. Only for esports now.', TRUE);

-- Reviews for ZOTAC GTX 1650 Super Twin Fan (product_id 37)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(37, 1, 'John', 4, 'Nice compact card with GDDR6. Good 1080p performance.', TRUE),
(37, 2, 'Mike', 5, 'Great upgrade from integrated graphics. Big difference.', TRUE),
(37, 3, 'Sarah', 4, 'The twin fans keep it cool and quiet in my small case.', TRUE);

-- Reviews for PowerColor RX 5500 XT Red Dragon (product_id 38)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(38, 3, 'Sarah', 4, 'Good budget card with nice RGB lighting.', TRUE),
(38, 1, 'John', 3, 'Decent 1080p performance but nothing special.', TRUE),
(38, 2, 'Mike', 5, 'Great value in the used market. Handles esports well.', TRUE);

-- Reviews for NVIDIA GeForce RTX 5090 (product_id 39)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(39, 2, 'Mike', 5, 'Absolute monster of a card. 8K gaming is finally possible!', TRUE),
(39, 3, 'Sarah', 4, 'Incredible performance but requires massive cooling.', TRUE),
(39, 1, 'John', 5, 'DLSS 4.0 is revolutionary. Doubles FPS in supported games.', TRUE);

-- Reviews for NVIDIA GeForce RTX 5080 (product_id 40)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(40, 1, 'John', 5, 'Perfect balance of price and performance in the high-end.', TRUE),
(40, 2, 'Mike', 4, 'Handles 4K gaming effortlessly. Great for content creation too.', TRUE),
(40, 3, 'Sarah', 5, 'The improved ray tracing performance is noticeable.', TRUE);

-- Reviews for AMD Radeon RX 8900 XTX (product_id 41)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(41, 3, 'Sarah', 5, 'AMD''s answer to the 5090. Performs amazingly well.', TRUE),
(41, 1, 'John', 4, 'Great for both gaming and compute workloads.', TRUE),
(41, 2, 'Mike', 5, 'FSR 4 is a game changer. Nearly matches DLSS 4 now.', TRUE);

-- Reviews for AMD Radeon RX 8800 XT (product_id 42)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(42, 2, 'Mike', 5, 'Best value high-end GPU. Competes with 5080 for less.', TRUE),
(42, 3, 'Sarah', 4, 'The chiplet design improves efficiency significantly.', TRUE),
(42, 1, 'John', 5, 'Perfect for 4K gaming. The 24GB VRAM is future-proof.', TRUE);

-- Reviews for ASUS Dual RTX 4060 Ti (product_id 43)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(43, 1, 'John', 4, 'Compact design fits small cases well. Good cooling.', TRUE),
(43, 2, 'Mike', 3, 'Decent but the 4060 Ti is starting to show its limits.', TRUE),
(43, 3, 'Sarah', 5, 'Perfect for my compact build. Handles 1080p great.', TRUE);

-- Reviews for MSI RTX 4060 Ventus 2X (product_id 44)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(44, 3, 'Sarah', 4, 'Great compact card for 1080p gaming.', TRUE),
(44, 1, 'John', 3, 'Basic but gets the job done. No frills design.', TRUE),
(44, 2, 'Mike', 5, 'Perfect for my small form factor build. Runs cool.', TRUE);

-- Reviews for Gigabyte RTX 4070 Windforce (product_id 45)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(45, 2, 'Mike', 5, 'Excellent cooling solution. The triple fans work great.', TRUE),
(45, 3, 'Sarah', 4, 'Good 1440p card. The Windforce design is reliable.', TRUE),
(45, 1, 'John', 5, 'Semi-passive fans are silent during light use. Love it.', TRUE);

-- Reviews for ASRock RX 7600 Challenger (product_id 46)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(46, 1, 'John', 4, 'Great budget option. The striped fans help with airflow.', TRUE),
(46, 2, 'Mike', 3, 'Decent for the price but nothing special.', TRUE),
(46, 3, 'Sarah', 5, 'Perfect for my nephew''s first gaming PC. Handles 1080p well.', TRUE);

-- Reviews for ZOTAC RTX 4070 Ti Trinity (product_id 47)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(47, 3, 'Sarah', 5, 'IceStorm 2.0 cooling works exceptionally well.', TRUE),
(47, 1, 'John', 4, 'Good looking card with nice RGB lighting.', TRUE),
(47, 2, 'Mike', 5, 'Compact for its performance level. Fits my case perfectly.', TRUE);

-- Reviews for PowerColor RX 7700 XT Hellhound (product_id 48)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(48, 2, 'Mike', 4, 'Hellhound cooling lives up to its name. Stays cool.', TRUE),
(48, 3, 'Sarah', 5, 'The ARGB lighting looks fantastic in my build.', TRUE),
(48, 1, 'John', 4, 'Good mid-range option. Handles 1440p well.', TRUE);

-- Reviews for Sapphire RX 7600 Pulse (product_id 49)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(49, 1, 'John', 4, 'Sapphire quality at a budget price. Great value.', TRUE),
(49, 2, 'Mike', 5, 'Perfect 1080p card. The Pulse cooling is efficient.', TRUE),
(49, 3, 'Sarah', 4, 'Compact size fits most cases. Reliable performance.', TRUE);

-- Reviews for XFX RX 7900 XT Speedster MERC (product_id 50)
INSERT INTO reviews (product_id, customer_id, user_first_name, rating, text, active) VALUES
(50, 3, 'Sarah', 5, 'XFX''s best cooler yet. Handles the 7900 XT with ease.', TRUE),
(50, 1, 'John', 4, 'Dual BIOS is a nice feature for different use cases.', TRUE),
(50, 2, 'Mike', 5, 'The massive heatsink keeps temps surprisingly low.', TRUE);
