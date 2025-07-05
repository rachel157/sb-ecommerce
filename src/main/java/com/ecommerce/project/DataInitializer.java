package com.ecommerce.project;

import com.ecommerce.project.model.*;
import com.ecommerce.project.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository, ProductRepository productRepository, BrandRepository brandRepository) {
        return args -> {


            // Định nghĩa các category
            Category c1 = new Category("Laptop");
            categoryRepository.save(c1);
            Category c2 = new Category("Camera");
            categoryRepository.save(c2);
            Category c3 = new Category("Other devices");
            categoryRepository.save(c3);

            //Tạo và lưu các đối tượng brand,set quan hệ của các brand với catgory c1
            Set<Category> lap = new HashSet<>(Arrays.asList(c1));
            Brand b1 = new Brand("Lenovo");
            b1.setCategories(lap);
            Brand b2 = new Brand("Apple");
            b2.setCategories(lap);
            Brand b3 = new Brand("Asus");
            b3.setCategories(lap);
            Brand b4 = new Brand("HP");
            b4.setCategories(lap);
            Brand b5 = new Brand("Dell");
            b5.setCategories(lap);
            Brand b6 = new Brand("Microsoft");
            b6.setCategories(lap);
            Brand b7 = new Brand("Razer");
            b7.setCategories(lap);
            Brand b8 = new Brand("Acer");
            b8.setCategories(lap);
            brandRepository.saveAll(List.of(b1, b2, b3, b4, b5, b6, b7, b8));

            Set<Category> cam = new HashSet<>(List.of(c2));
            Brand b9  = new Brand("Nikon");       b9.setCategories(cam);
            Brand b10 = new Brand("Canon");       b10.setCategories(cam);
            Brand b11 = new Brand("Sony");        b11.setCategories(cam);
            Brand b12 = new Brand("Fujifilm");    b12.setCategories(cam);
            Brand b13 = new Brand("OM System");   b13.setCategories(cam);
            Brand b14 = new Brand("Olympus");     b14.setCategories(cam);
            Brand b15 = new Brand("Hasselblad");  b15.setCategories(cam);
            brandRepository.saveAll(List.of(b9, b10, b11, b12, b13, b14, b15));

            Set<Category> other = new HashSet<>(List.of(c3));
            Brand b16 = new Brand("OnePlus");     b16.setCategories(other);
            Brand b17 = new Brand("Amazfit");     b17.setCategories(other);
            Brand b18 = new Brand("HP");          b18.setCategories(other); // HP xuất hiện ở cả Laptop và Other
            Brand b19 = new Brand("Halliday");    b19.setCategories(other);
            Brand b20 = new Brand("Dreame");      b20.setCategories(other);
            brandRepository.saveAll(List.of(b16, b17, b18, b19, b20));

            //Thiết lập mối quan hệ giữa category c1 với các brand
            Set<Brand> lapBrands = new HashSet<>(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8));
            c1.setBrands(lapBrands);
            categoryRepository.save(c1);

            Set<Brand> camBrands = new HashSet<>(Arrays.asList(b9, b10, b11, b12, b13, b14, b15));
            c2.setBrands(camBrands);
            categoryRepository.save(c2);

            Set<Brand> otherBrands = new HashSet<>(Arrays.asList(b16, b17, b18, b19, b20));
            c3.setBrands(otherBrands);
            categoryRepository.save(c3);






            // Tạo và lưu các đối tượng Product
            Product p1 = new Product("Apple MacBook Air 13-inch (M4, 2025)", "1-Photoroom.png", "MacBook Air mới nhất với chip M4, màn hình Liquid Retina 13.6 inch, bộ nhớ thống nhất lên đến 24GB và SSD 2TB. Hoàn hảo cho sinh viên và chuyên gia.", 5, 999.0, 10, 899.1, c1);
            Product p2 = new Product("Apple MacBook Air 15-inch (M4, 2025)", "2-Photoroom.png", "Phiên bản màn hình lớn của MacBook Air với chip M4, màn hình Liquid Retina 15.3 inch, bộ nhớ thống nhất 24GB và SSD 2TB.", 3, 1199.0, 10, 1079.1, c1);
            Product p3 = new Product("Apple MacBook Pro 14-inch (M4, 2025)", "3-Photoroom.png", "MacBook Pro hiệu năng cao với chip M4, màn hình Liquid Retina XDR 14.2 inch, bộ nhớ thống nhất 36GB và SSD 8TB. Lý tưởng cho chuyên gia sáng tạo.", 2, 1999.0, 5, 1899.05, c1);
            Product p4 = new Product("Microsoft Surface Laptop 7", "4-Photoroom.png", "Surface Laptop mới nhất với bộ xử lý Snapdragon X Elite, màn hình PixelSense 13.8 inch, RAM 64GB và SSD 1TB.", 4, 1000.0, 15, 850.0, c1);
            Product p5 = new Product("Asus Zenbook A14", "5-Photoroom.png", "Laptop nhỏ gọn với bộ xử lý Snapdragon X, màn hình OLED 14 inch, RAM 16GB và SSD 1TB.", 6, 1100.0, 10, 990.0, c1);
            Product p6 = new Product("Lenovo Yoga Slim 9i 14 Gen 10 (2025)", "6-Photoroom.png", "Laptop siêu mỏng với bộ xử lý Intel Core Ultra, màn hình OLED 3K 14 inch, RAM 32GB và SSD 1TB.", 3, 1499.0, 8, 1379.12, c1);
            Product p7 = new Product("HP Pavilion Plus 14 (2025)", "7-Photoroom.png", "Laptop thời trang với bộ xử lý AMD Ryzen, màn hình WUXGA 14 inch, RAM 16GB và SSD 512GB.", 5, 899.99, 12, 789.99, c1);
            Product p8 = new Product("Dell XPS 13 (9350, Intel Core Ultra 2nd Gen)", "8-Photoroom.png", "Ultrabook cao cấp với bộ xử lý Intel Core Ultra, màn hình InfinityEdge 13.4 inch, RAM 16GB và SSD 512GB.", 4, 1299.0, 10, 1169.1, c1);
            Product p9 = new Product("Dell Inspiron 14 Plus (7441)", "9-Photoroom.png", "Laptop giá trị với bộ xử lý Snapdragon X Plus, màn hình 2.2K 14 inch, RAM 16GB và SSD 256GB.", 7, 799.99, 15, 679.99, c1);
            Product p10 = new Product("Razer Blade 16 (2025)", "10-Photoroom.png", "Laptop chơi game cao cấp với bộ xử lý AMD Ryzen AI 9, màn hình QHD+ 16 inch, RAM 32GB và SSD 1TB.", 2, 2799.99, 5, 2659.99, c1);
            Product p11 = new Product("Asus ROG Zephyrus G16", "11-Photoroom.png", "Laptop chơi game mạnh mẽ với bộ xử lý Intel Core Ultra 9, màn hình QHD OLED 16 inch, RAM 16GB và Nvidia GeForce RTX 4080.", 3, 2699.99, 10, 2429.99, c1);
            Product p12 = new Product("Acer Predator Helios Neo 16", "12-Photoroom.png", "Laptop chơi game giá phải chăng với bộ xử lý Intel Core i5, màn hình WUXGA 16 inch, RAM 16GB và Nvidia GeForce RTX 4050.", 4, 1049.99, 10, 944.99, c1);
            Product p13 = new Product("HP EliteBook Ultra G1i", "13-Photoroom.png", "Laptop doanh nghiệp với bộ xử lý Intel Core Ultra 7, màn hình OLED 14 inch, RAM 16GB và SSD 512GB.", 5, 1999.0, 8, 1839.12, c1);
            Product p14 = new Product("Lenovo ThinkPad X9 15 Aura Edition (2025)", "14-Photoroom.png", "Laptop doanh nghiệp bền bỉ với bộ xử lý Intel Core Ultra, màn hình WQUXGA 15.6 inch, RAM 32GB và SSD 1TB.", 3, 1699.99, 10, 1529.99, c1);
            Product p15 = new Product("Lenovo Yoga 9i 2-in-1 Aura Edition 14 (2025)", "15-Photoroom.png", "Laptop 2 trong 1 linh hoạt với bộ xử lý Intel Core Ultra, màn hình cảm ứng OLED 14 inch, RAM 16GB và SSD 1TB.", 4, 1399.99, 12, 1227.99, c1);
            Product p16 = new Product("Microsoft Surface Pro 11", "16-Photoroom.png", "Máy tính bảng-laptop linh hoạt với bộ xử lý Snapdragon X Elite, màn hình OLED 13 inch, RAM 64GB và SSD 1TB.", 5, 999.99, 10, 899.99, c1);
            Product p17 = new Product("Asus Vivobook S 15 Copilot+", "17-Photoroom.png", "Laptop hỗ trợ AI với bộ xử lý Snapdragon X Elite, màn hình OLED 15.6 inch, RAM 16GB và SSD 1TB.", 3, 1299.99, 10, 1169.99, c1);
            Product p18 = new Product("Dell XPS 17 (9730)", "18-Photoroom.png", "Laptop màn hình lớn với bộ xử lý Intel Core i7, màn hình UHD+ 17 inch, RAM 16GB và SSD 512GB.", 2, 2499.99, 5, 2374.99, c1);
            Product p19 = new Product("Apple MacBook Pro 16-inch (M4 Pro)", "19-Photoroom.png", "MacBook Pro cao cấp với chip M4 Pro, màn hình Liquid Retina XDR 16 inch, bộ nhớ thống nhất 36GB và SSD 8TB.", 1, 2499.0, 0, 2499.0, c1);
            Product p20 = new Product("Lenovo IdeaPad Duet 5 Chromebook", "20-Photoroom.png", "Chromebook giá rẻ với bộ xử lý Qualcomm Snapdragon, màn hình Full HD 13.3 inch, RAM 8GB và eMMC 128GB.", 10, 499.99, 15, 425.00, c1);
            Product p21 = new Product("Nikon Z6 III", "21-Photoroom.png", "Camera mirrorless full-frame tiên tiến với cảm biến 24MP, chụp 20fps, video 6K và lấy nét tự động xuất sắc.", 3, 2499.95, 10, 2249.96, c2);
            Product p22 = new Product("Canon EOS R10", "22-Photoroom.png", "Camera mirrorless cấp nhập môn với cảm biến APS-C 24.2MP, chụp 23fps, video 4K và thiết kế nhỏ gọn.", 5, 979.99, 15, 833.00, c2);
            Product p23 = new Product("Sony A7R V", "23-Photoroom.png", "Camera full-frame độ phân giải cao với cảm biến 61MP, chụp 10fps, video 8K và lấy nét tự động tiên tiến.", 2, 3998.0, 5, 3798.1, c2);
            Product p24 = new Product("Fujifilm X-T5", "24-Photoroom.png", "Camera APS-C dành cho người đam mê với cảm biến 40.2MP, chụp 15fps, video 6.2K và thiết kế cổ điển.", 4, 1699.99, 10, 1529.99, c2);
            Product p25 = new Product("OM System OM-1 II", "25-Photoroom.png", "Camera Micro Four Thirds với cảm biến 20.4MP, chụp 120fps, video 4K và khả năng chống chịu thời tiết.", 3, 1999.99, 12, 1759.99, c2);
            Product p26 = new Product("Fujifilm X100VI", "26-Photoroom.png", "Camera compact cao cấp với cảm biến X-Trans 40.2MP, chụp 11fps, video 6.2K và ống kính cố định 23mm.", 2, 1599.99, 8, 1463.99, c2);
            Product p27 = new Product("Olympus OM-D E-M10 Mark IV", "27-Photoroom.png", "Camera Micro Four Thirds giá rẻ với cảm biến 20.3MP, chụp 15fps, video 4K và ổn định hình ảnh 5 trục.", 6, 699.99, 15, 595.00, c2);
            Product p28 = new Product("Nikon D7500", "28-Photoroom.png", "Camera DSLR với cảm biến APS-C 20.9MP, chụp 8fps, video 1080p và màn hình cảm ứng xoay.", 4, 1099.95, 10, 989.96, c2);
            Product p29 = new Product("Fujifilm Instax Mini 12", "29-Photoroom.png", "Camera chụp ảnh lấy ngay cho người mới với ống kính 60mm, đèn flash tích hợp và dễ sử dụng.", 10, 0, 20, 64.00, c2);
            Product p30 = new Product("Nikon Z9", "30-Photoroom.png", "Camera mirrorless full-frame chuyên nghiệp với cảm biến 45.7MP, chụp 20fps raw, video 8K và tính năng tiên tiến.", 1, 5499.95, 0, 5499.95, c2);
            Product p31 = new Product("Hasselblad X2D 100C", "31-Photoroom.png", "Camera medium format với cảm biến 100MP, ổn định hình ảnh 7 cấp và chất lượng hình ảnh cao.", 1, 7999.0, 0, 7999.0, c2);
            Product p32 = new Product("Canon EOS 5D Mark IV", "32-Photoroom.png", "DSLR chuyên nghiệp với cảm biến full-frame 30.4MP, chụp 7fps, video 4K và thiết kế chắc chắn.", 2, 2499.00, 10, 2249.10, c2);
            Product p33 = new Product("Sony A7 IV", "33-Photoroom.png", "Camera mirrorless full-frame với cảm biến 33MP, chụp 10fps, video 4K và lấy nét tự động xuất sắc.", 3, 2498.0, 10, 2248.2, c2);
            Product p34 = new Product("Canon EOS R6 Mark II", "34-Photoroom.png", "Camera mirrorless full-frame với cảm biến 24.2MP, chụp 40fps, video 4K và tính năng tiên tiến.", 4, 2499.99, 10, 2249.99, c2);
            Product p35 = new Product("Fujifilm GFX 100S II", "35-Photoroom.png", "Camera medium format với cảm biến 102MP, ổn định hình ảnh 5 trục và khả năng chụp ảnh độ phân giải cao.", 1, 4999.99, 5, 4749.99, c2);
            Product p36 = new Product("OnePlus 13", "36-Photoroom.png", "Điện thoại thông minh hàng đầu từ OnePlus với tính năng tiên tiến, bộ xử lý mạnh mẽ và thiết kế cao cấp.", 10, 999.99, 10, 899.99, c3);
            Product p37 = new Product("Amazfit Active 2", "37-Photoroom.png", "Đồng hồ thông minh với màn hình AMOLED, bản đồ ngoại tuyến, theo dõi giấc ngủ và tập luyện, thiết kế thép không gỉ.", 15, 0, 15, 169.99, c3);
            Product p38 = new Product("HP All-in-One Desktop", "38-Photoroom.png", "Máy tính tất cả trong một với màn hình cảm ứng 27 hoặc 32 inch, bộ xử lý Intel Core Ultra và webcam tích hợp.", 5, 1299.99, 10, 1169.99, c3);
            Product p39 = new Product("Halliday Smart Glasses", "39-Photoroom.png", "Kính thông minh hiển thị màn hình kỹ thuật số, tích hợp AI và điều khiển bằng nhẫn thông minh.", 3, 499.99, 5, 474.99, c3);
            Product p40 = new Product("Dreame X50", "40-Photoroom.png", "Robot hút bụi tiên tiến với chân thu gọn, lực hút mạnh và tính năng điều hướng thông minh.", 7, 699.99, 12, 611.99, c3);


            // Các sản phẩm Laptop (p1 → p20) – đã có sẵn trong câu trước
            p1.setBrand(b2);  // Apple
            p2.setBrand(b2);  // Apple
            p3.setBrand(b2);  // Apple
            p4.setBrand(b6);  // Microsoft
            p5.setBrand(b3);  // Asus
            p6.setBrand(b1);  // Lenovo
            p7.setBrand(b4);  // HP
            p8.setBrand(b5);  // Dell
            p9.setBrand(b5);  // Dell
            p10.setBrand(b7); // Razer
            p11.setBrand(b3); // Asus
            p12.setBrand(b8); // Acer
            p13.setBrand(b4); // HP
            p14.setBrand(b1); // Lenovo
            p15.setBrand(b1); // Lenovo
            p16.setBrand(b6); // Microsoft
            p17.setBrand(b3); // Asus
            p18.setBrand(b5); // Dell
            p19.setBrand(b2); // Apple
            p20.setBrand(b1); // Lenovo

// Các sản phẩm Camera (p21 → p35)
            p21.setBrand(b9);  // Nikon
            p22.setBrand(b10); // Canon
            p23.setBrand(b11); // Sony
            p24.setBrand(b12); // Fujifilm
            p25.setBrand(b13); // OM System
            p26.setBrand(b12); // Fujifilm
            p27.setBrand(b14); // Olympus
            p28.setBrand(b9);  // Nikon
            p29.setBrand(b12); // Fujifilm
            p30.setBrand(b9);  // Nikon
            p31.setBrand(b15); // Hasselblad
            p32.setBrand(b10); // Canon
            p33.setBrand(b11); // Sony
            p34.setBrand(b10); // Canon
            p35.setBrand(b12); // Fujifilm

// Các sản phẩm Other devices (p36 → p40)
            p36.setBrand(b16); // OnePlus
            p37.setBrand(b17); // Amazfit
            p38.setBrand(b18); // HP (b18: HP của Other devices)
            p39.setBrand(b19); // Halliday
            p40.setBrand(b20); // Dreame

// Lưu toàn bộ sản phẩm vào DB
            productRepository.saveAll(List.of(
                    p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
                    p11, p12, p13, p14, p15, p16, p17, p18, p19, p20,
                    p21, p22, p23, p24, p25, p26, p27, p28, p29, p30,
                    p31, p32, p33, p34, p35, p36, p37, p38, p39, p40
            ));

            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);

            //Tạo address ứng với user1

            Address address1 = new Address(
                    "123 Main St",
                    "Building A",
                    "Ho Chi Minh City",
                    "Ho Chi Minh",
                    "Vietnam",
                    "700000"
            );

            Address address2 = new Address(
                    "456 Le Loi",
                    "Sun Tower",
                    "Da Nang",
                    "Da Nang",
                    "Vietnam",
                    "550000"
            );

            Address address3 = new Address(
                    "789 Nguyen Hue",
                    "Skyline Residence",
                    "Hanoi",
                    "Hanoi",
                    "Vietnam",
                    "100000"
            );


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                address1.setUser(user1);
                address2.setUser(user1);
                address3.setUser(user1);
                user1.getAddresses().add(address1);
                user1.getAddresses().add(address2);
                user1.getAddresses().add(address3);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
                User seller1 = new User("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller1").ifPresent(seller -> {
                seller.setRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });

        };
    }

}
