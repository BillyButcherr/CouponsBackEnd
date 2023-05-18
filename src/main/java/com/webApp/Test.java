package com.webApp;

public class Test {
//    private final ConfigurableApplicationContext context;
//    public Test(ConfigurableApplicationContext context) {
//        this.context = context;
//    }
//
//    public void testAll() {
//        CouponExpirationDailyJob couponExpirationDailyJob = context.getBean(CouponExpirationDailyJob.class);
//        Thread t1 = new Thread(couponExpirationDailyJob);
//        TokenExpirationJob tokenExpirationJob = context.getBean(TokenExpirationJob.class);
//        Thread t2 = new Thread(tokenExpirationJob);
//        t2.start();
//        try {
//            LoginManager loginManager = context.getBean(LoginManager.class);
//            // ----- Admin Service, companies CRUD ------
//            System.out.println("             ----- Admin Service, companies CRUD ------\n");
//            AdminService adminService = (AdminService)loginManager.Login("admin@admin.com", "admin", ClientType.Administrator);
//            long company1ID = adminService.addCompany(new Company(
//                    "Gal-Corp",
//                    "gal@galcorp.com",
//                    "1234Pass"
//            ));
//            long company2ID = adminService.addCompany(new Company(
//                    "JohnTech",
//                    "John@tech.com",
//                    "12345"
//            ));
//            long company3ID = adminService.addCompany(new Company(
//                    "Mark",
//                    "Mark@suits.com",
//                    "1234"
//            ));
//            System.out.println("All companies : ");
//            adminService.getAllCompanies().forEach(System.out::println);
//            adminService.updateCompany(new Company(
//                    company3ID,
//                    "Mark",
//                    "Swims@best.com",
//                    "pa$$word",
//                    adminService.getOneCompany(company3ID).getCoupons()
//            ));
//            System.out.println("The updated company : ");
//            System.out.println(adminService.getOneCompany(company3ID));
//            System.out.println("Deleting company " + company3ID);
//            adminService.deleteCompany(company3ID);
//
//            System.out.println("All companies : ");
//            adminService.getAllCompanies().forEach(System.out::println);
//            // ----- Admin Service, customers CRUD ------
//            System.out.println("            ----- Admin Service, customers CRUD ------\n");
//            long customer1ID = adminService.addCustomer(new Customer(
//                    "mark",
//                    "shark",
//                    "cow@rules.com",
//                    "1234"
//            ));
//            long customer2ID = adminService.addCustomer(new Customer(
//                    "folk",
//                    "music",
//                    "cool@artists.com",
//                    "bestPass"
//            ));long customer3ID = adminService.addCustomer(new Customer(
//                    "free-folk",
//                    "train",
//                    "bob@bob.org",
//                    "$tron9Pa$$"
//            ));
//            System.out.println("All customers :");
//            adminService.getAllCustomers().forEach(System.out::println);
//            adminService.updateCustomer(new Customer(
//                    customer2ID,
//                    "rock",
//                    "music",
//                    "Rolling@stones.com",
//                    "bestPass",
//                    null
//            ));
//            System.out.println("The updated Customer : ");
//            System.out.println(adminService.getOneCustomer(customer2ID));
//            System.out.println("Deleting customer " + customer3ID);
//            adminService.deleteCustomer(customer3ID);
//            System.out.println("All customers :");
//            adminService.getAllCustomers().forEach(System.out::println);
//
//            // ------ Company Service, coupons CRUD -------
//            System.out.println("             ------ Company Service, coupons CRUD -------\n");
//            CompanyService companyService = (CompanyService)loginManager.Login("gal@galcorp.com", "1234Pass", ClientType.Company);
//            System.out.println("Company information : ");
//            System.out.println(companyService.getCompanyDetails());
//            Coupon coupon1 = new Coupon(
//                    adminService.getOneCompany(company1ID),
//                    Category.Vacation,
//                    "Miami - South Beach",
//                    "10 Days of fun",
//                    "2023-01-14",
//                    "2023-12-14",
//                    15,
//                    1500,
//                    "url"
//            );
//            Coupon coupon2 = new Coupon(
//                    adminService.getOneCompany(company1ID),
//                    Category.Vacation,
//                    "Spain - Palma de Mallorca",
//                    "5 Days of sun",
//                    "2023-01-05",
//                    "2023-12-28",
//                    5,
//                    800,
//                    "url"
//            );
//            Coupon coupon3 = new Coupon(
//                    adminService.getOneCompany(company2ID),
//                    Category.Food,
//                    "Mexican bar",
//                    "best tacos",
//                    "2023-01-10",
//                    "2023-12-22",
//                    30,
//                    200,
//                    "url"
//            );
//            Coupon coupon4 = new Coupon(
//                    adminService.getOneCompany(company2ID),
//                    Category.Restaurant,
//                    "Italian bar",
//                    "best pasta",
//                    "2023-01-15",
//                    "2023-12-25",
//                    30,
//                    200,
//                    "url"
//            );
//            Coupon coupon5 = new Coupon(
//                    adminService.getOneCompany(company1ID),
//                    Category.Electricity,
//                    "Electric oven",
//                    "best oven",
//                    "2023-01-15",
//                    "2023-12-25",
//                    30,
//                    1200,
//                    "url"
//            );Coupon coupon6 = new Coupon(
//                    adminService.getOneCompany(company1ID),
//                    Category.Electricity,
//                    "50' TV Screen",
//                    "best TV",
//                    "2023-01-15",
//                    "2023-12-25",
//                    40,
//                    3000,
//                    "url"
//            );
//
//            long coupon1ID = companyService.addCoupon(coupon1);
//            long coupon2ID = companyService.addCoupon(coupon2);
//            long coupon3ID = companyService.addCoupon(coupon3);
//            long coupon4ID = companyService.addCoupon(coupon4);
//            long coupon5ID = companyService.addCoupon(coupon5);
//            long coupon6ID = companyService.addCoupon(coupon6);
//
//            System.out.println("Coupons added");
//
//            t1.start(); // start now after coupons added
//
//            System.out.println("All company coupons: ");
//            companyService.getAllCompanyCoupons().forEach(System.out::println);
//            System.out.println("Update coupon " + coupon2ID);
//            companyService.updateCoupon(new Coupon(
//                    coupon2ID,
//                    adminService.getOneCompany(company1ID),
//                    Category.Restaurant,
//                    "Italian bar - updated bar",
//                    "best updated pasta",
//                    "2023-01-15",
//                    "2023-12-25",
//                    6565,
//                    990,
//                    "url"
//            ));
//            System.out.println("Deleting coupon " + coupon1ID);
//            companyService.deleteCoupon(coupon1ID);
//            System.out.println("All company coupons: ");
//            companyService.getAllCompanyCoupons().forEach(System.out::println);
//            System.out.println("Company coupons filtered by category(Electricity) : ");
//            companyService.getAllCompanyCoupons(Category.Electricity).forEach(System.out::println);
//            System.out.println("Company coupons filtered by price(1000) : ");
//            companyService.getAllCompanyCoupons(1000).forEach(System.out::println);
//
//
//
//            // ------ Customer Service, coupons CRUD -------
//            System.out.println("             ------ Customer Service, coupons CRUD -------\n");
//
//            CustomerService customerService = (CustomerService)loginManager.Login("cow@rules.com", "1234", ClientType.Customer);
//            System.out.println("Customer information : ");
//            System.out.println(customerService.getCustomerDetails());
//            System.out.println("Purchase coupons...");
//            //customerService.purchaseCoupon(coupon1);// Attempt to buy a deleted coupon
//            customerService.purchaseCoupon(coupon2.getId());
//            System.out.println("Purchased coupon " + coupon2.getId());
//            customerService.purchaseCoupon(coupon3.getId());
//            System.out.println("Purchased coupon " + coupon3.getId());
//            customerService.purchaseCoupon(coupon4.getId());
//            System.out.println("Purchased coupon " + coupon4.getId());
//
//
//            System.out.println("All customer coupons : ");
//            customerService.getCustomerPurchasedCoupons().forEach(System.out::println);
//            System.out.println("All customer coupons by category(Restaurant) :");
//            customerService.getCustomerPurchasedCoupons(Category.Restaurant).forEach(System.out::println);
//            System.out.println("All customer coupons by max price(300) :");
//            customerService.getCustomerPurchasedCoupons(300).forEach(System.out::println);
//
//            //check manual cascade
////            adminService.deleteCustomer(1);
////            adminService.deleteCompany(1);
//
//            System.out.println("Test completed.");
//        }
//        catch (CustomException e){
//            System.out.println(e.getMessage());
//        }
//        catch (Exception ignored){
////            System.out.println(ignored);
//        }
//        finally {
//            couponExpirationDailyJob.stop();
//            t1.interrupt();
//            tokenExpirationJob.stop();
//            t2.interrupt();
//        }
//    }
}
