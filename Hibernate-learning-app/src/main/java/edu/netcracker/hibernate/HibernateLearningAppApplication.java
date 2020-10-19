package edu.netcracker.hibernate;

import edu.netcracker.hibernate.dao.DAO;
import edu.netcracker.hibernate.dao.PartnerProductFamilyDao;
import edu.netcracker.hibernate.entity.PartnerORM;
import edu.netcracker.hibernate.entity.ProductFamilyORM;
import edu.netcracker.hibernate.entity.RetailerORM;
import edu.netcracker.hibernate.repository.PartnerRepository;
import edu.netcracker.hibernate.repository.ProductFamilyRepository;
import edu.netcracker.hibernate.repository.ProgramProductRepository;
import edu.netcracker.hibernate.repository.ProgramRepository;
import edu.netcracker.hibernate.repository.RetailerRepository;
import edu.netcracker.hibernate.repository.SettlementRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class HibernateLearningAppApplication {
    private RetailerRepository retailerRepository;

    private ProgramRepository programRepository;

    private ProgramProductRepository programProductRepository;

    private PartnerRepository partnerRepository;

    private ProductFamilyRepository productFamilyRepository;

    private SettlementRepository settlementRepository;

    private DAO<PartnerORM, Integer> partnerDao;

    private PartnerProductFamilyDao partnerProductFamilyDao;

    public HibernateLearningAppApplication(RetailerRepository retailerRepository,
                                           ProgramRepository programRepository,
                                           ProgramProductRepository programProductRepository,
                                           PartnerRepository partnerRepository,
                                           ProductFamilyRepository productFamilyRepository,
                                           SettlementRepository settlementRepository,
                                           DAO<PartnerORM, Integer> partnerDao,
                                           PartnerProductFamilyDao partnerProductFamilyDao) {
        this.retailerRepository = retailerRepository;
        this.programRepository = programRepository;
        this.programProductRepository = programProductRepository;
        this.partnerRepository = partnerRepository;
        this.productFamilyRepository = productFamilyRepository;
        this.settlementRepository = settlementRepository;
        this.partnerDao = partnerDao;
        this.partnerProductFamilyDao = partnerProductFamilyDao;


//        AtomicInteger productId = new AtomicInteger(0);
//        AtomicInteger partnerId = new AtomicInteger(0);
//
//        RetailerORM retailerORM = new RetailerORM()
//                .setEmail(ThreadLocalRandom.current().ints().limit(2).collect(() -> new StringJoiner(""),
//                        (stringJoiner, value) -> stringJoiner.add(Integer.toString(value)), StringJoiner::merge).toString())
//                .setIcon("icon")
//                .setName("name")
//                .setPassword("password");
//
//        retailerORM = retailerRepository.save(retailerORM);
//
//        List<ProductFamilyORM> productFamilies1 = Arrays.asList(new ProductFamilyORM()
//                .setPpfId(9)
//                        .setProductFamilyId(1)
//                        .setProductFamilyName("ppf 1"),
//                new ProductFamilyORM()
//                        .setProductFamilyId(2)
//                        .setProductFamilyName("ppf 2"));
//
//        List<ProductFamilyORM> productFamilies2 = Arrays.asList(new ProductFamilyORM()
//                        .setPpfId(11)
//                        .setProductFamilyId(1)
//                        .setProductFamilyName("ppf 1"),
//                new ProductFamilyORM()
//                        .setProductFamilyId(2)
//                        .setProductFamilyName("ppf 2"));
//
//        List<ProductFamilyORM> productFamilies3 = Arrays.asList(new ProductFamilyORM()
//                        .setPpfId(9)
//                        .setProductFamilyId(1)
//                        .setProductFamilyName("ppf 1"),
//                new ProductFamilyORM()
//                        .setProductFamilyId(2)
//                        .setProductFamilyName("ppf 2"));
//
//        List<PartnerORM> partners = Arrays.asList(
//                new PartnerORM()
//                        .setAccountNum("partner1 acc")
//                        .setCustomerRef("partner1 custRef")
//                        .setIcon("icon")
//                        .setPartnerName("name 1")
//                        .setProductFamilies(
//                                productFamilies1)
//                        .setRetailer(retailerORM),
//                new PartnerORM()
//                        .setAccountNum("partner2 acc")
//                        .setCustomerRef("partner2 custRef")
//                        .setIcon("icon")
//                        .setPartnerName("name 2")
//                        .setProductFamilies(
//                                productFamilies2)
//                        .setRetailer(retailerORM),
//                new PartnerORM()
//                        .setAccountNum("partner3 acc")
//                        .setCustomerRef("partner3 custRef")
//                        .setIcon("icon")
//                        .setPartnerName("name 3")
//                        .setProductFamilies(
//                                productFamilies3)
//                        .setRetailer(retailerORM));
//
//        List<PartnerORM> finalPartners = partners;
//        productFamilies1.forEach(productFamilyORM -> productFamilyORM.setPartner(finalPartners.get(partnerId.get())));
//        partnerId.incrementAndGet();
//        productFamilies2.forEach(productFamilyORM -> productFamilyORM.setPartner(finalPartners.get(partnerId.get())));
//        partnerId.incrementAndGet();
//        productFamilies3.forEach(productFamilyORM -> productFamilyORM.setPartner(finalPartners.get(partnerId.get())));
//
//        partners = partnerDao.saveAll(partners);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        List<List<ProgramProductORM>> programProducts = Arrays.asList(Arrays.asList(
//                new ProgramProductORM()
//                        .setLabel("label 1")
//                        .setPartnerType(PartnerType.Retail)
//                        .setProductId(productId.getAndIncrement())
//                        .setSettlements(Arrays.asList(
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.BILL)
//                                        .setChargeType(ChargeType.TERMINATION)
//                                        .setFixedAmount(123.5)
//                                        .setRevenueShareAmount(1),
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.PAY)
//                                        .setChargeType(ChargeType.ACTIVATION)
//                                        .setFixedAmount(456.85)
//                                        .setRevenueShareAmount(3),
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.PURCHASE)
//                                        .setChargeType(ChargeType.USAGE)
//                                        .setFixedAmount(756.2)
//                                        .setRevenueShareAmount(2))),
//                new ProgramProductORM()
//                        .setLabel("label 2")
//                        .setPartnerType(PartnerType.ServicePartner)
//                        .setProductId(productId.getAndIncrement())
//                        .setSettlements(Arrays.asList(
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.BILL)
//                                        .setChargeType(ChargeType.TERMINATION)
//                                        .setFixedAmount(753.5)
//                                        .setRevenueShareAmount(4),
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.PAY)
//                                        .setChargeType(ChargeType.ACTIVATION)
//                                        .setFixedAmount(12.5)
//                                        .setRevenueShareAmount(5),
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.PURCHASE)
//                                        .setChargeType(ChargeType.USAGE)
//                                        .setFixedAmount(756.2)
//                                        .setRevenueShareAmount(6))),
//                new ProgramProductORM()
//                        .setLabel("label 3")
//                        .setPartnerType(PartnerType.Sponsor)
//                        .setProductId(productId.getAndIncrement())
//                        .setSettlements(Arrays.asList(
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.BILL)
//                                        .setChargeType(ChargeType.TERMINATION)
//                                        .setFixedAmount(9436.5)
//                                        .setRevenueShareAmount(7),
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.PAY)
//                                        .setChargeType(ChargeType.ACTIVATION)
//                                        .setFixedAmount(1452.35)
//                                        .setRevenueShareAmount(8),
//                                new SettlementORM()
//                                        .setSettlementEvent(SettlementEvent.PURCHASE)
//                                        .setChargeType(ChargeType.USAGE)
//                                        .setFixedAmount(15.22)
//                                        .setRevenueShareAmount(9)))
//                ),
//                Arrays.asList(
//                        new ProgramProductORM()
//                                .setLabel("label 4")
//                                .setPartnerType(PartnerType.Retail)
//                                .setProductId(productId.getAndIncrement())
//                                .setSettlements(Arrays.asList(
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.BILL)
//                                                .setChargeType(ChargeType.TERMINATION)
//                                                .setFixedAmount(123.5)
//                                                .setRevenueShareAmount(10),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PAY)
//                                                .setChargeType(ChargeType.ACTIVATION)
//                                                .setFixedAmount(456.85)
//                                                .setRevenueShareAmount(11),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PURCHASE)
//                                                .setChargeType(ChargeType.USAGE)
//                                                .setFixedAmount(756.2)
//                                                .setRevenueShareAmount(12))),
//                        new ProgramProductORM()
//                                .setLabel("label 5")
//                                .setPartnerType(PartnerType.ServicePartner)
//                                .setProductId(productId.getAndIncrement())
//                                .setSettlements(Arrays.asList(
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.BILL)
//                                                .setChargeType(ChargeType.TERMINATION)
//                                                .setFixedAmount(753.5)
//                                                .setRevenueShareAmount(13),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PAY)
//                                                .setChargeType(ChargeType.ACTIVATION)
//                                                .setFixedAmount(12.5)
//                                                .setRevenueShareAmount(14),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PURCHASE)
//                                                .setChargeType(ChargeType.USAGE)
//                                                .setFixedAmount(756.2)
//                                                .setRevenueShareAmount(15))),
//                        new ProgramProductORM()
//                                .setLabel("label 6")
//                                .setPartnerType(PartnerType.Sponsor)
//                                .setProductId(productId.getAndIncrement())
//                                .setSettlements(Arrays.asList(
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.BILL)
//                                                .setChargeType(ChargeType.TERMINATION)
//                                                .setFixedAmount(9436.5)
//                                                .setRevenueShareAmount(16),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PAY)
//                                                .setChargeType(ChargeType.ACTIVATION)
//                                                .setFixedAmount(1452.35)
//                                                .setRevenueShareAmount(17),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PURCHASE)
//                                                .setChargeType(ChargeType.USAGE)
//                                                .setFixedAmount(15.22)
//                                                .setRevenueShareAmount(18)))
//                ), Arrays.asList(
//                        new ProgramProductORM()
//                                .setLabel("label 7")
//                                .setPartnerType(PartnerType.Retail)
//                                .setProductId(productId.getAndIncrement())
//                                .setSettlements(Arrays.asList(
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.BILL)
//                                                .setChargeType(ChargeType.TERMINATION)
//                                                .setFixedAmount(123.5)
//                                                .setRevenueShareAmount(19),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PAY)
//                                                .setChargeType(ChargeType.ACTIVATION)
//                                                .setFixedAmount(456.85)
//                                                .setRevenueShareAmount(20),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PURCHASE)
//                                                .setChargeType(ChargeType.USAGE)
//                                                .setFixedAmount(756.2)
//                                                .setRevenueShareAmount(21))),
//                        new ProgramProductORM()
//                                .setLabel("label 8")
//                                .setPartnerType(PartnerType.ServicePartner)
//                                .setProductId(productId.getAndIncrement())
//                                .setSettlements(Arrays.asList(
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.BILL)
//                                                .setChargeType(ChargeType.TERMINATION)
//                                                .setFixedAmount(753.5)
//                                                .setRevenueShareAmount(22),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PAY)
//                                                .setChargeType(ChargeType.ACTIVATION)
//                                                .setFixedAmount(12.5)
//                                                .setRevenueShareAmount(23),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PURCHASE)
//                                                .setChargeType(ChargeType.USAGE)
//                                                .setFixedAmount(756.2)
//                                                .setRevenueShareAmount(24))),
//                        new ProgramProductORM()
//                                .setLabel("label 9")
//                                .setPartnerType(PartnerType.Sponsor)
//                                .setProductId(productId.getAndIncrement())
//                                .setSettlements(Arrays.asList(
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.BILL)
//                                                .setChargeType(ChargeType.TERMINATION)
//                                                .setFixedAmount(9436.5)
//                                                .setRevenueShareAmount(25),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PAY)
//                                                .setChargeType(ChargeType.ACTIVATION)
//                                                .setFixedAmount(1452.35)
//                                                .setRevenueShareAmount(26),
//                                        new SettlementORM()
//                                                .setSettlementEvent(SettlementEvent.PURCHASE)
//                                                .setChargeType(ChargeType.USAGE)
//                                                .setFixedAmount(15.22)
//                                                .setRevenueShareAmount(27)))
//                ));
//
//        AtomicInteger i = new AtomicInteger();
//        List<ProgramORM> programs = Arrays.asList(
//                new ProgramORM()
//                        .setName("pr1 name")
//                        .setEndDate(Instant.now().plus(Duration.ofDays(5)))
//                        .setStartDate(Instant.now().plus(Duration.ofHours(2)))
//                        .setRetailerProductId(1)
//                        .setProgramProducts(programProducts.get(0)
//                                .stream()
//                                .peek(programProductORM -> programProductORM.setPartner(finalPartners.get(i.get() == 2 ? i.getAndSet(0) : i.getAndIncrement())))
//                                .collect(Collectors.toList()))
//                        .setRetailer(retailerORM),
//                new ProgramORM()
//                        .setName("pr2 name")
//                        .setEndDate(Instant.now().plus(Duration.ofDays(5)))
//                        .setStartDate(Instant.now().plus(Duration.ofHours(2)))
//                        .setRetailerProductId(2)
//                        .setProgramProducts(programProducts.get(1).stream()
//                                .peek(programProductORM -> programProductORM.setPartner(finalPartners.get(i.get() == 2 ? i.getAndSet(0) : i.getAndIncrement())))
//                                .collect(Collectors.toList()))
//                        .setRetailer(retailerORM),
//                new ProgramORM()
//                        .setName("pr3 name")
//                        .setEndDate(Instant.now().plus(Duration.ofDays(5)))
//                        .setStartDate(Instant.now().plus(Duration.ofHours(2)))
//                        .setRetailerProductId(3)
//                        .setProgramProducts(programProducts.get(2).stream()
//                                .peek(programProductORM -> programProductORM.setPartner(finalPartners.get(i.get() == 2 ? i.getAndSet(0) : i.getAndIncrement())))
//                                .collect(Collectors.toList()))
//                        .setRetailer(retailerORM)
//        );
//
//        programRepository.saveAll(programs);

        RetailerORM retailerORM = new RetailerORM()
                .setEmail(ThreadLocalRandom.current().ints().limit(2).collect(() -> new StringJoiner(""),
                        (stringJoiner, value) -> stringJoiner.add(Integer.toString(value)), StringJoiner::merge).toString())
                .setIcon("icon")
                .setName("name")
                .setPassword("password");

        retailerORM = retailerRepository.save(retailerORM);


        PartnerORM partnerORM = new PartnerORM()
                .setAccountNum("partner1 acc")
                .setCustomerRef("partner1 custRef")
                .setIcon("icon")
                .setPartnerName("name 1")
                .setRetailer(retailerORM);

        List<ProductFamilyORM> productFamilies = Arrays.asList(new ProductFamilyORM()
                        .setProductFamilyId(1)
                        .setProductFamilyName("family Name 1")
                        .setPartner(partnerORM),
                new ProductFamilyORM()
                        .setProductFamilyId(11)
                        .setProductFamilyName("family name 12")
                        .setPartner(partnerORM));

        partnerORM.setProductFamilies(productFamilies);

        partnerORM = partnerDao.save(partnerORM);

        System.out.println("***************************************");
        System.out.println("After save!");

        System.out.println(partnerDao.findAll());

        System.out.println(partnerProductFamilyDao.findAll());

        List<ProductFamilyORM> productFamilies2 = Arrays.asList(new ProductFamilyORM()
                        .setProductFamilyId(35)
                        .setProductFamilyName("family new Name")
                        .setPartner(partnerORM),
                new ProductFamilyORM()
                        .setProductFamilyId(35)
                        .setProductFamilyName("family new name")
                        .setPartner(partnerORM));

        partnerORM.setAccountNum("new Partner acc1")
                .setIcon("new icon)")
                .setPartnerName("Hello!")
                .setProductFamilies(productFamilies2);

        partnerORM = partnerDao.update(partnerORM);
        System.out.println();
        System.out.println("***************************************");
        System.out.println("After update!");

        System.out.println(partnerDao.findAll());

        System.out.println(partnerProductFamilyDao.findAll());

        partnerDao.delete(partnerORM.getPartnerId());

        System.out.println();
        System.out.println("***************************************");
        System.out.println("After delete!");
        System.out.println(partnerDao.findAll());

        System.out.println(partnerProductFamilyDao.findAll());
    }

    public static void main(String[] args) {
        SpringApplication.run(HibernateLearningAppApplication.class, args);
    }
}
