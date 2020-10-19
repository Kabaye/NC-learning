package edu.netcracker.hibernate;

import edu.netcracker.hibernate.dao.DAO;
import edu.netcracker.hibernate.dao.PartnerProductFamilyDao;
import edu.netcracker.hibernate.dao.ProgramDao;
import edu.netcracker.hibernate.entity.PartnerORM;
import edu.netcracker.hibernate.entity.ProductFamilyORM;
import edu.netcracker.hibernate.entity.ProgramORM;
import edu.netcracker.hibernate.entity.ProgramProductORM;
import edu.netcracker.hibernate.entity.RetailerORM;
import edu.netcracker.hibernate.entity.SettlementORM;
import edu.netcracker.hibernate.entity.enumirate.ChargeType;
import edu.netcracker.hibernate.entity.enumirate.PartnerType;
import edu.netcracker.hibernate.entity.enumirate.SettlementEvent;
import edu.netcracker.hibernate.repository.PartnerRepository;
import edu.netcracker.hibernate.repository.ProductFamilyRepository;
import edu.netcracker.hibernate.repository.ProgramProductRepository;
import edu.netcracker.hibernate.repository.ProgramRepository;
import edu.netcracker.hibernate.repository.RetailerRepository;
import edu.netcracker.hibernate.repository.SettlementRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class HibernateLearningAppApplication {
    private final RetailerRepository retailerRepository;

    private ProgramRepository programRepository;

    private ProgramProductRepository programProductRepository;

    private PartnerRepository partnerRepository;

    private ProductFamilyRepository productFamilyRepository;

    private SettlementRepository settlementRepository;

    private DAO<PartnerORM, Integer> partnerDao;

    private PartnerProductFamilyDao partnerProductFamilyDao;

    private ProgramDao programDao;

    public HibernateLearningAppApplication(RetailerRepository retailerRepository,
                                           ProgramRepository programRepository,
                                           ProgramProductRepository programProductRepository,
                                           PartnerRepository partnerRepository,
                                           ProductFamilyRepository productFamilyRepository,
                                           SettlementRepository settlementRepository,
                                           DAO<PartnerORM, Integer> partnerDao,
                                           PartnerProductFamilyDao partnerProductFamilyDao, ProgramDao programDao) {
        this.retailerRepository = retailerRepository;
        this.programRepository = programRepository;
        this.programProductRepository = programProductRepository;
        this.partnerRepository = partnerRepository;
        this.productFamilyRepository = productFamilyRepository;
        this.settlementRepository = settlementRepository;
        this.partnerDao = partnerDao;
        this.partnerProductFamilyDao = partnerProductFamilyDao;
        this.programDao = programDao;

//        checkSaveUpdatePartner();

        checkSaveUpdateDeleteProgram();
    }

    private void checkSaveUpdatePartner() {
        RetailerORM retailerORM = new RetailerORM()
                .setEmail(getRandomNumericString())
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

        partnerORM = partnerRepository.save(partnerORM);

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

        partnerORM = partnerRepository.save(partnerORM);

        partnerRepository.deleteById(partnerORM.getPartnerId());

    }

    private void checkSaveUpdateDeleteProgram() {
        RetailerORM retailerORM = new RetailerORM()
                .setEmail(getRandomNumericString())
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

        partnerORM = partnerRepository.save(partnerORM);

        ProgramORM programORM = new ProgramORM()
                .setName("pr1 name")
                .setEndDate(Instant.now().plus(Duration.ofDays(5)))
                .setStartDate(Instant.now().minus(Duration.ofHours(2)))
                .setRetailerProductId(1)
                .setRetailer(retailerORM);

        List<ProgramProductORM> programProducts = Arrays.asList(
                new ProgramProductORM()
                        .setLabel("label 7")
                        .setPartnerType(PartnerType.Retail)
                        .setProductId(1)
                        .setProgram(programORM)
                        .setPartner(partnerORM),
                new ProgramProductORM()
                        .setLabel("label 8")
                        .setPartnerType(PartnerType.ServicePartner)
                        .setProductId(2)
                        .setProgram(programORM)
                        .setPartner(partnerORM),
                new ProgramProductORM()
                        .setLabel("label 9")
                        .setPartnerType(PartnerType.Sponsor)
                        .setProductId(3)
                        .setProgram(programORM)
                        .setPartner(partnerORM)
        );
        final List<SettlementORM> settlements1 = Arrays.asList(
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.BILL)
                        .setChargeType(ChargeType.TERMINATION)
                        .setFixedAmount(123.5)
                        .setRevenueShareAmount(19)
                        .setProduct(programProducts.get(0)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PAY)
                        .setChargeType(ChargeType.ACTIVATION)
                        .setFixedAmount(456.85)
                        .setRevenueShareAmount(20)
                        .setProduct(programProducts.get(0)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PURCHASE)
                        .setChargeType(ChargeType.USAGE)
                        .setFixedAmount(756.2)
                        .setRevenueShareAmount(21)
                        .setProduct(programProducts.get(0)));
        final List<SettlementORM> settlements2 = Arrays.asList(
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.BILL)
                        .setChargeType(ChargeType.TERMINATION)
                        .setFixedAmount(753.5)
                        .setRevenueShareAmount(22)
                        .setProduct(programProducts.get(1)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PAY)
                        .setChargeType(ChargeType.ACTIVATION)
                        .setFixedAmount(12.5)
                        .setRevenueShareAmount(23)
                        .setProduct(programProducts.get(1)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PURCHASE)
                        .setChargeType(ChargeType.OTS)
                        .setFixedAmount(756.2)
                        .setRevenueShareAmount(24)
                        .setProduct(programProducts.get(1)));
        final List<SettlementORM> settlements3 = Arrays.asList(
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.BILL)
                        .setChargeType(ChargeType.TERMINATION)
                        .setFixedAmount(9436.5)
                        .setRevenueShareAmount(25)
                        .setProduct(programProducts.get(2)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PAY)
                        .setChargeType(ChargeType.OTS)
                        .setFixedAmount(1452.35)
                        .setRevenueShareAmount(26)
                        .setProduct(programProducts.get(2)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PURCHASE)
                        .setChargeType(ChargeType.USAGE)
                        .setFixedAmount(15.22)
                        .setRevenueShareAmount(27)
                        .setProduct(programProducts.get(2)));

        programProducts.get(0).setSettlements(settlements1);
        programProducts.get(1).setSettlements(settlements2);
        programProducts.get(2).setSettlements(settlements3);

        programORM.setProgramProducts(programProducts);

        programORM = programRepository.save(programORM);

        List<ProgramProductORM> programProducts2 = Arrays.asList(
                new ProgramProductORM()
                        .setLabel("label new 1")
                        .setPartnerType(PartnerType.ServicePartner)
                        .setProductId(100)
                        .setProgram(programORM)
                        .setPartner(partnerORM),
                new ProgramProductORM()
                        .setLabel("label new 1")
                        .setPartnerType(PartnerType.Sponsor)
                        .setProductId(200)
                        .setProgram(programORM)
                        .setPartner(partnerORM)
        );
        final List<SettlementORM> settlements4 = Arrays.asList(
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.BILL)
                        .setChargeType(ChargeType.TERMINATION)
                        .setFixedAmount(123.5)
                        .setRevenueShareAmount(19)
                        .setProduct(programProducts2.get(0)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PAY)
                        .setChargeType(ChargeType.TERMINATION)
                        .setFixedAmount(12.5666)
                        .setRevenueShareAmount(10)
                        .setProduct(programProducts2.get(0)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PURCHASE)
                        .setChargeType(ChargeType.USAGE)
                        .setFixedAmount(756.2)
                        .setRevenueShareAmount(11)
                        .setProduct(programProducts2.get(0)));

        final List<SettlementORM> settlements5 = Arrays.asList(
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.BILL)
                        .setChargeType(ChargeType.USAGE)
                        .setFixedAmount(753.5)
                        .setRevenueShareAmount(12555)
                        .setProduct(programProducts2.get(1)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PAY)
                        .setChargeType(ChargeType.USAGE)
                        .setFixedAmount(12.5)
                        .setRevenueShareAmount(222)
                        .setProduct(programProducts2.get(1)),
                new SettlementORM()
                        .setSettlementEvent(SettlementEvent.PURCHASE)
                        .setChargeType(ChargeType.RECURRING)
                        .setFixedAmount(756.2)
                        .setRevenueShareAmount(24563)
                        .setProduct(programProducts2.get(1)));

        programProducts2.get(0).setSettlements(settlements4);
        programProducts2.get(1).setSettlements(settlements5);

        programORM.setName("new name 2.0")
                .setEndDate(Instant.now().plus(Duration.ofDays(15)))
                .setStartDate(Instant.now().minus(Duration.ofDays(10)))
                .setRetailerProductId(6666)
                .setProgramProducts(programProducts2);

        programORM = programRepository.save(programORM);

        programRepository.deleteById(programORM.getProgramId());
    }

    private String getRandomNumericString() {
        return ThreadLocalRandom.current().ints().limit(2).collect(() -> new StringJoiner(""),
                (stringJoiner, value) -> stringJoiner.add(Integer.toString(value)), StringJoiner::merge).toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(HibernateLearningAppApplication.class, args);
    }
}
