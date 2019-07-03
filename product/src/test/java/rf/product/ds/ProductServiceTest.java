package rf.product.ds;


import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import rf.foundation.utils.JsonHelper;
import rf.product.model.*;
import rf.product.model.enums.*;
import rf.product.repository.ProductDao;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private JsonHelper jsonHelper;

    @Test
    public void peristProduct() throws Exception{

        ProductSpec product = initSampleProduct();

        productService.saveProduct(product);

    }


//    @Test
    public void loadProduct(){
        ProductSpec product = productService.findProduct("EH");

        String json = jsonHelper.toJSON(product);

        System.out.print("JSON:" + json);

    }

    private ProductSpec initSampleProduct(){
        ProductSpec product = new ProductSpec();
        product.setName("百万医疗");
        product.setCode("EH");
        product.setEffectiveDate(new DateTime(2019,1,1,0,0,0).toDate());
        product.setExpiredDate(new DateTime(2020,12,31,0,0,0).toDate());
        product.setStatus(ProductStatus.EFFECTIVE);
        product.setVersion("1.0");
        product.setFixedCoverage(true);

        InsuredObjectSpec insured = new InsuredObjectSpec();
        insured.setCate(InsuredObjectCate.PERSON);
        insured.setMultiple(false);
        product.getSubComponents().add(insured);

        CoverageSpec cover1 = new CoverageSpec();
        cover1.setName("一般医疗保险金");
        cover1.setCode("COM_HC");
        cover1.setPrimary(true);
        product.getSubComponents().add(cover1);

        LimitSpec limit1 = new LimitSpec();
        limit1.setIndemnityType(IndemnityType.AOP);
        limit1.setPattern(LimitPartten.APO.getValue());
        limit1.setFixed(true);
        limit1.setDefaultValue("limitAmount:1000000.00");
        cover1.getSubComponents().add(limit1);

        CoverageSpec cover2 = new CoverageSpec();
        cover2.setName("恶性肿瘤医疗保险金");
        cover2.setCode("CA");
        cover2.setPrimary(true);
        cover2.setStatus(CoverageStatus.ENABLE);
        product.getSubComponents().add(cover2);

        LimitSpec limit2= new LimitSpec();
        limit2.setIndemnityType(IndemnityType.AOP);
        limit2.setPattern(LimitPartten.APO.getValue());
        limit2.setFixed(true);
        limit2.setDefaultValue("limitAmount:1000000.00");
        cover2.getSubComponents().add(limit2);

        RuleSetSpec ruleSet = new RuleSetSpec();
        ruleSet.setName("核保");
        ruleSet.setCode("RS_UW");
        product.getSubComponents().add(ruleSet);

        RuleSpec rule = new RuleSpec();
        rule.setName("被保险人年龄");
        rule.setCode("R_AGE");
        rule.setType(RuleType.VALIDATION);
        rule.setBody("INSURED_AGE > 60");
        List<String> rfactors = Lists.newArrayList();
        rfactors.add("INSURED_AGE");
        rule.setFactors(rfactors);
        rule.setMessage("被保险人年龄不超过60岁");
        product.getSubComponents().add(rule);

        List<String> rules = Lists.newArrayList();
        rules.add("R_AGE");
        ruleSet.setRefRules(rules);

        FeeSpec fee1 = new FeeSpec();
        fee1.setName("手续费");
        fee1.setCode("ADMIN_FEE");
        fee1.setBizCate(FeeBizCate.TAX_FEE);
        fee1.setAsPremium(true);
        fee1.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee1);

        FormulaSpec formula4 = new FormulaSpec();
        formula4.setName("手续费计算公式");
        formula4.setCode("F_ADMIN_FEE");
        formula4.setPurpose(FormulaPurpose.TAX_FEE);
        List<String> factors4 = Lists.newArrayList();
        factors4.add("SNP");
        formula4.setFactors(factors4);
        formula4.setBody(" x = SNP * 0.01; return [ADMIN_FEE:x]");
        fee1.getSubComponents().add(formula4);

        FeeSpec fee2 = new FeeSpec();
        fee2.setName("佣金");
        fee2.setCode("COMMISSION");
        fee2.setBizCate(FeeBizCate.COMMISSION);
        fee2.setAsPremium(false);
        fee2.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee2);

        FormulaSpec formula5 = new FormulaSpec();
        formula5.setName("佣金计算公式");
        formula5.setCode("F_COMMISSION");
        formula5.setPurpose(FormulaPurpose.COMMISSION);
        List<String> factors5 = Lists.newArrayList();
        factors5.add("SNP");
        formula5.setFactors(factors5);
        formula5.setBody(" x = SNP * 0.1; return [COMMISSION:x]");
        fee2.getSubComponents().add(formula5);

        FeeSpec fee3 = new FeeSpec();
        fee3.setName("实际支付保费");
        fee3.setCode("APP");
        fee3.setBizCate(FeeBizCate.PREMIUM);
        fee3.setAsPremium(true);
        fee3.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee3);

        FormulaSpec formula6 = new FormulaSpec();
        formula6.setName("实际支付保费计算公式");
        formula6.setCode("F_APP");
        formula6.setPurpose(FormulaPurpose.APP);
        List<String> factors6 = Lists.newArrayList();
        factors6.add("SNP");
        factors6.add("ADMIN_FEE");
        formula6.setFactors(factors6);
        formula6.setBody(" x = SNP + ADMIN_FEE; return [APP:x]");
        fee3.getSubComponents().add(formula6);

        FeeSpec fee4 = new FeeSpec();
        fee4.setName("毛保费");
        fee4.setCode("SGP");
        fee4.setBizCate(FeeBizCate.PREMIUM);
        fee4.setAsPremium(true);
        fee4.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee4);

        FormulaSpec formula1 = new FormulaSpec();
        formula1.setName("保费公式");
        formula1.setCode("F_PREMIUM_EH");
        formula1.setPurpose(FormulaPurpose.PREMIUM);
        List<String> factors1 = Lists.newArrayList();
        factors1.add("INSURED_AGE");
        factors1.add("SOCIAL_INSURANCE");
        formula1.setFactors(factors1);
        formula1.setBody("x =  RateTable('RATE_COM_HC',['INSURED_AGE':INSURED_AGE,'SOCIAL_INSURANCE':SOCIAL_INSURANCE]); return ['SGP':x,'SNP':x];");
        fee4.getSubComponents().add(formula1);

        FeeSpec fee5 = new FeeSpec();
        fee5.setName("净保费");
        fee5.setCode("SNP");
        fee5.setBizCate(FeeBizCate.PREMIUM);
        fee5.setAsPremium(true);
        fee5.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee5);

        FormulaSpec formula2 = new FormulaSpec();
        formula2.setName("保费公式");
        formula2.setCode("F_PREMIUM_EH");
        formula2.setPurpose(FormulaPurpose.PREMIUM);
        List<String> factors2 = Lists.newArrayList();
        factors2.add("INSURED_AGE");
        factors2.add("SOCIAL_INSURANCE");
        formula2.setFactors(factors2);
        formula2.setBody("x =  RateTable('RATE_COM_HC',['INSURED_AGE':INSURED_AGE,'SOCIAL_INSURANCE':SOCIAL_INSURANCE]); return ['SGP':x,'SNP':x];");
        fee5.getSubComponents().add(formula2);

        EndorsementSpec endo = new EndorsementSpec();
        endo.setCode("CANCELLATION_FROM_MIDWAY");
        endo.setName("中途退保");
        endo.setEndorsementType(EndorsementType.CANCELLATION);
        product.getSubComponents().add(endo);

        FormulaSpec formula7 = new FormulaSpec();
        formula7.setName("中途退保收退费计算公式");
        formula7.setCode("F_CANCELLATION_FROM_MIDWAY");
        formula7.setPurpose(FormulaPurpose.PREMIUM);
        List<String> factors7 = Lists.newArrayList();
        factors7.add("ORIGINAL_PREMIUM");
        factors7.add("ENDO_PRO_RATE");
        formula7.setFactors(factors7);
        formula7.setBody(" x = ORIGINAL_PREMIUM * ENDO_PRO_RATE; return [SNP:x]");
        endo.getSubComponents().add(formula7);

        FormulaSpec formula8 = new FormulaSpec();
        formula8.setName("手续费计算公式");
        formula8.setCode("F_ADMIN_FEE");
        formula8.setPurpose(FormulaPurpose.TAX_FEE);
        List<String> factors8 = Lists.newArrayList();
        factors8.add("SNP");
        formula8.setFactors(factors4);
        formula8.setBody(" x = SNP * 0.01; return [ADMIN_FEE:x]");
        endo.getSubComponents().add(formula8);

        FormulaSpec formula9 = new FormulaSpec();
        formula9.setName("佣金计算公式");
        formula9.setCode("F_COMMISSION");
        formula9.setPurpose(FormulaPurpose.COMMISSION);
        List<String> factors9 = Lists.newArrayList();
        factors9.add("SNP");
        formula9.setFactors(factors9);
        formula9.setBody(" x = SNP * 0.1; return [COMMISSION:x]");
        endo.getSubComponents().add(formula9);

        FormulaSpec formula10 = new FormulaSpec();
        formula10.setName("实际支付保费计算公式");
        formula10.setCode("F_APP");
        formula10.setPurpose(FormulaPurpose.APP);
        List<String> factors10 = Lists.newArrayList();
        factors10.add("SNP");
        factors10.add("ADMIN_FEE");
        formula10.setFactors(factors6);
        formula10.setBody(" x = SNP + ADMIN_FEE; return [APP:x]");
        endo.getSubComponents().add(formula10);

        RateTableSpec tableSpec = new RateTableSpec();
        tableSpec.setCode("RATE_COM_HC");
        tableSpec.setName("一般医疗险费率表");

        product.getSubComponents().add(tableSpec);

        return product;
    }

    private ProductSpec initProduct(){
        ProductSpec product = new ProductSpec();
        product.setName("百万医疗");
        product.setCode("EHI");
        product.setEffectiveDate(new DateTime(2019,1,1,0,0,0).toDate());
        product.setExpiredDate(new DateTime(2020,12,31,0,0,0).toDate());
        product.setStatus(ProductStatus.EFFECTIVE);
        product.setVersion("1.0");
        product.setFixedCoverage(true);

        InsuredObjectSpec insured = new InsuredObjectSpec();
        insured.setCate(InsuredObjectCate.PERSON);
        insured.setMultiple(false);
        product.getSubComponents().add(insured);

        CoverageSpec cover1 = new CoverageSpec();
        cover1.setName("一般医疗保险金");
        cover1.setCode("COM_HC");
        cover1.setPrimary(true);
        product.getSubComponents().add(cover1);

        LimitSpec limit1 = new LimitSpec();
        limit1.setIndemnityType(IndemnityType.AOP);
        limit1.setPattern(LimitPartten.APO.getValue());
        limit1.setFixed(true);
        limit1.setDefaultValue("limitAmount:1000000.00");
        cover1.getSubComponents().add(limit1);

        FormulaSpec formula1 = new FormulaSpec();
        formula1.setName("一般医疗保险金保费公式");
        formula1.setCode("F_PREMIUM_COM_HC");
        formula1.setPurpose(FormulaPurpose.PREMIUM);
        List<String> factors1 = Lists.newArrayList();
        factors1.add("INSURED_AGE");
        factors1.add("SOCIAL_INSURANCE");
        formula1.setFactors(factors1);
        formula1.setBody("x =  RateTable('RATE_COM_HC',['INSURED_AGE':INSURED_AGE,'SOCIAL_INSURANCE':SOCIAL_INSURANCE]); return ['SGP':x,'SNP':x];");
        cover1.getSubComponents().add(formula1);


        CoverageSpec cover2 = new CoverageSpec();
        cover2.setName("恶性肿瘤医疗保险金");
        cover2.setCode("CA");
        cover2.setPrimary(true);
        cover2.setStatus(CoverageStatus.ENABLE);
        product.getSubComponents().add(cover2);

        LimitSpec limit2= new LimitSpec();
        limit2.setIndemnityType(IndemnityType.AOP);
        limit2.setPattern(LimitPartten.APO.getValue());
        limit2.setFixed(true);
        limit2.setDefaultValue("limitAmount:1000000.00");
        cover2.getSubComponents().add(limit2);

        FormulaSpec formula2 = new FormulaSpec();
        formula2.setName("恶性肿瘤医疗保险金保费公式");
        formula2.setCode("F_PREMIUM_CA");
        formula2.setPurpose(FormulaPurpose.PREMIUM);
        List<String> factors2 = Lists.newArrayList();
        factors2.add("INSURED_AGE");
        factors2.add("SOCIAL_INSURANCE");
        formula2.setFactors(factors2);
        formula2.setBody("x =  RateTable('RATE_CA',['INSURED_AGE':INSURED_AGE,'SOCIAL_INSURANCE':SOCIAL_INSURANCE]); return ['SGP':x,'SNP':x];");
        cover2.getSubComponents().add(formula2);

//        CoverageSpec cover3 = new CoverageSpec();
//        cover3.setName("住院补贴");
//        cover3.setCode("HOS_ALW");
//        cover3.setIsPrimary(true);
//        cover3.setStatus(CoverageStatus.ENABLE);
//        product.getSubComponents().add(cover3);
//
//        LimitSpec limit3 = new LimitSpec();
//        limit3.setIndemnityType(IndemnityType.AOA);
//        limit3.setPattern(LimitPartten.APUPO_MN.getValue());
//        limit3.setFixed(true);
//        limit3.setDefaultValue("unitAmount:50.00,numberOfUnit:1,unitType:天,maxNumberOfUnit:60");
//        cover3.getSubComponents().add(limit3);
//
//        FormulaSpec formula3 = new FormulaSpec();
//        formula3.setName("住院补贴保费计算公式");
//        formula3.setCode("F_PREMIUM_HOS_ALW");
//        formula3.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors3 = Lists.newArrayList();
//        factors3.add("AOA_LIMIT_AMOUNT");
//        formula3.setFactors(factors3);
//        formula3.setBody("x = AOA_LIMIT_AMOUNT * 0.01; return ['SGP':x];");
//        cover3.getSubComponents().add(formula3);

        RuleSetSpec ruleSet = new RuleSetSpec();
        ruleSet.setName("核保");
        ruleSet.setCode("RS_UW");
        product.getSubComponents().add(ruleSet);

        RuleSpec rule = new RuleSpec();
        rule.setName("被保险人年龄");
        rule.setCode("R_AGE");
        rule.setType(RuleType.VALIDATION);
        rule.setBody("INSURED_AGE > 60");
        List<String> rfactors = Lists.newArrayList();
        rfactors.add("INSURED_AGE");
        rule.setFactors(rfactors);
        rule.setMessage("被保险人年龄不超过60岁");
        product.getSubComponents().add(rule);

        List<String> rules = Lists.newArrayList();
        rules.add("R_AGE");
        ruleSet.setRefRules(rules);

        FeeSpec fee1 = new FeeSpec();
        fee1.setName("手续费");
        fee1.setCode("ADMIN_FEE");
        fee1.setBizCate(FeeBizCate.TAX_FEE);
        fee1.setAsPremium(true);
        fee1.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee1);

        FormulaSpec formula4 = new FormulaSpec();
        formula4.setName("手续费计算公式");
        formula4.setCode("F_ADMIN_FEE");
        formula4.setPurpose(FormulaPurpose.TAX_FEE);
        List<String> factors4 = Lists.newArrayList();
        factors4.add("SNP");
        formula4.setFactors(factors4);
        formula4.setBody(" x = SNP * 0.01; return [ADMIN_FEE:x]");
        fee1.getSubComponents().add(formula4);

        FeeSpec fee2 = new FeeSpec();
        fee2.setName("佣金");
        fee2.setCode("COMMISSION");
        fee2.setBizCate(FeeBizCate.COMMISSION);
        fee2.setAsPremium(false);
        fee2.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee2);

        FormulaSpec formula5 = new FormulaSpec();
        formula5.setName("佣金计算公式");
        formula5.setCode("F_COMMISSION");
        formula5.setPurpose(FormulaPurpose.COMMISSION);
        List<String> factors5 = Lists.newArrayList();
        factors5.add("SNP");
        formula5.setFactors(factors5);
        formula5.setBody(" x = SNP * 0.1; return [COMMISSION:x]");
        fee2.getSubComponents().add(formula5);

        FeeSpec fee3 = new FeeSpec();
        fee3.setName("实际支付保费");
        fee3.setCode("APP");
        fee3.setBizCate(FeeBizCate.PREMIUM);
        fee3.setAsPremium(true);
        fee3.setComposeFrom(FeeComposeFrom.FORMULA);
        product.getSubComponents().add(fee3);

        FormulaSpec formula6 = new FormulaSpec();
        formula6.setName("实际支付保费计算公式");
        formula6.setCode("F_APP");
        formula6.setPurpose(FormulaPurpose.APP);
        List<String> factors6 = Lists.newArrayList();
        factors6.add("SNP");
        factors6.add("ADMIN_FEE");
        formula6.setFactors(factors6);
        formula6.setBody(" x = SNP + ADMIN_FEE; return [APP:x]");
        fee3.getSubComponents().add(formula6);

        FeeSpec fee4 = new FeeSpec();
        fee4.setName("毛保费");
        fee4.setCode("SGP");
        fee4.setBizCate(FeeBizCate.PREMIUM);
        fee4.setAsPremium(true);
        fee4.setComposeFrom(FeeComposeFrom.COVERAGE);
        product.getSubComponents().add(fee4);

        FeeSpec fee5 = new FeeSpec();
        fee5.setName("净保费");
        fee5.setCode("SNP");
        fee5.setBizCate(FeeBizCate.PREMIUM);
        fee5.setAsPremium(true);
        fee5.setComposeFrom(FeeComposeFrom.COVERAGE);
        product.getSubComponents().add(fee5);

        EndorsementSpec endo = new EndorsementSpec();
        endo.setCode("CANCELLATION_FROM_MIDWAY");
        endo.setName("中途退保");
        endo.setEndorsementType(EndorsementType.CANCELLATION);
        product.getSubComponents().add(endo);

        FormulaSpec formula7 = new FormulaSpec();
        formula7.setName("中途退保收退费计算公式");
        formula7.setCode("F_CANCELLATION_FROM_MIDWAY");
        formula7.setPurpose(FormulaPurpose.PREMIUM);
        List<String> factors7 = Lists.newArrayList();
        factors7.add("ORIGINAL_PREMIUM");
        factors7.add("ENDO_PRO_RATE");
        formula7.setFactors(factors7);
        formula7.setBody(" x = ORIGINAL_PREMIUM * ENDO_PRO_RATE; return [SNP:x]");
        endo.getSubComponents().add(formula7);

        FormulaSpec formula8 = new FormulaSpec();
        formula8.setName("手续费计算公式");
        formula8.setCode("F_ADMIN_FEE");
        formula8.setPurpose(FormulaPurpose.TAX_FEE);
        List<String> factors8 = Lists.newArrayList();
        factors8.add("SNP");
        formula8.setFactors(factors4);
        formula8.setBody(" x = SNP * 0.01; return [ADMIN_FEE:x]");
        endo.getSubComponents().add(formula8);


        FormulaSpec formula9 = new FormulaSpec();
        formula9.setName("佣金计算公式");
        formula9.setCode("F_COMMISSION");
        formula9.setPurpose(FormulaPurpose.COMMISSION);
        List<String> factors9 = Lists.newArrayList();
        factors9.add("SNP");
        formula9.setFactors(factors9);
        formula9.setBody(" x = SNP * 0.1; return [COMMISSION:x]");
        endo.getSubComponents().add(formula9);

        FormulaSpec formula10 = new FormulaSpec();
        formula10.setName("实际支付保费计算公式");
        formula10.setCode("F_APP");
        formula10.setPurpose(FormulaPurpose.APP);
        List<String> factors10 = Lists.newArrayList();
        factors10.add("SNP");
        factors10.add("ADMIN_FEE");
        formula10.setFactors(factors6);
        formula10.setBody(" x = SNP + ADMIN_FEE; return [APP:x]");
        endo.getSubComponents().add(formula10);

        return product;
    }

//    private ProductSpec initMSProduct(){
//        ProductSpec product = new ProductSpec();
//        product.setName("民生e生无忧费用补偿医疗保险");
//        product.setCode("900001");
//        product.setOrganization("310000");
//        product.setEffectiveDate(new DateTime(2017,7,1,0,0,0).toDate());
//        product.setExpiredDate(new DateTime(2099,12,31,0,0,0).toDate());
//        product.setStatus(ProductStatus.EFFECTIVE);
//        product.setVersion("1.0");
//        product.setFixedCoverage(true);
//
//        InsuredObjectSpec insured = new InsuredObjectSpec();
//        insured.setCate(InsuredObjectCate.PERSON);
//        insured.setIsMultiple(false);
//        product.getSubComponents().add(insured);
//
//        CoverageSpec cover1 = new CoverageSpec();
//        cover1.setName("一般医疗保险金");
//        cover1.setCode("07000001");
//        cover1.setPrimary(true);
//        product.getSubComponents().add(cover1);
//
//        LimitSpec limit1 = new LimitSpec();
//        limit1.setIndemnityType(IndemnityType.AOP);
//        limit1.setPattern(LimitPartten.APO.getValue());
//        limit1.setFixed(true);
//        limit1.setDefaultValue("limitAmount:3000000.00");
//        cover1.getSubComponents().add(limit1);
//
//        DeductibleSpec deductible1 = new DeductibleSpec();
//        deductible1.setPattern(DeductiblePartten.DEDUCTIBLE_ADMOUNT.getValue());
//        deductible1.setFixed(true);
//        deductible1.setDefaultValue("deductibleAmount:10000.00");
//        cover1.getSubComponents().add(deductible1);
//
//        FormulaSpec formula1 = new FormulaSpec();
//        formula1.setName("e生无忧医疗保险保费公式");
//        formula1.setCode("F_PREMIUM_MSH");
//        formula1.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors1 = Lists.newArrayList();
//        factors1.add("INSURED_AGE");
//        factors1.add("SOCIAL_INSURANCE");
//        factors1.add("RENEWAL");
//        formula1.setFactors(factors1);
//        formula1.setBody("x =  RateTable('RATE_MSH',['INSURED_AGE':INSURED_AGE,'SOCIAL_INSURANCE':SOCIAL_INSURANCE,'RENEWAL':RENEWAL]); return ['SGP':x,'SNP':x];");
//        cover1.getSubComponents().add(formula1);
//
//        CoverageSpec cover2 = new CoverageSpec();
//        cover2.setName("重大疾病医疗保险金");
//        cover2.setCode("07000002");
//        cover2.setPrimary(true);
//        cover2.setStatus(CoverageStatus.ENABLE);
//        product.getSubComponents().add(cover2);
//
//        LimitSpec limit2= new LimitSpec();
//        limit2.setIndemnityType(IndemnityType.AOP);
//        limit2.setPattern(LimitPartten.APO.getValue());
//        limit2.setFixed(true);
//        limit2.setDefaultValue("limitAmount:3180000.00");
//        cover2.getSubComponents().add(limit2);
//
//        DeductibleSpec deductible2 = new DeductibleSpec();
//        deductible2.setPattern(DeductiblePartten.DEDUCTIBLE_ADMOUNT.getValue());
//        deductible2.setFixed(true);
//        deductible2.setDefaultValue("deductibleAmount:0.00");
//        cover2.getSubComponents().add(deductible2);
//
//        FormulaSpec formula2 = new FormulaSpec();
//        formula2.setName("0保费公式");
//        formula2.setCode("F_PREMIUM_ZERO");
//        formula2.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors2 = Lists.newArrayList();
//        formula2.setFactors(factors2);
//        formula2.setBody("x =  0.00; return ['SGP':x,'SNP':x];");
//        cover2.getSubComponents().add(formula2);
//
//        RuleSetSpec ruleSet = new RuleSetSpec();
//        ruleSet.setName("核保");
//        ruleSet.setCode("RS_UW");
//        product.getSubComponents().add(ruleSet);
//
//        RuleSpec rule = new RuleSpec();
//        rule.setName("被保险人年龄");
//        rule.setCode("R_AGE");
//        rule.setType(RuleType.VALIDATION);
//        rule.setBody("RENEWAL == '0' && INSURED_AGE > 60");
//        List<String> rfactors = Lists.newArrayList();
//        rfactors.add("INSURED_AGE");
//        rfactors.add("RENEWAL");
//        rule.setFactors(rfactors);
//        rule.setMessage("被保险人年龄不能超过60岁。");
//        product.getSubComponents().add(rule);
//
//        RuleSpec rule2 = new RuleSpec();
//        rule2.setName("被保险人年龄（天数）");
//        rule2.setCode("R_AGE_DAYS");
//        rule2.setType(RuleType.VALIDATION);
//        rule2.setBody("INSURED_AGE_DAYS < 28");
//        List<String> rfactors2 = Lists.newArrayList();
//        rfactors2.add("INSURED_AGE_DAYS");
//        rule2.setFactors(rfactors2);
//        rule2.setMessage("被保险人年龄不能低于28天。");
//        product.getSubComponents().add(rule2);
//
//        List<String> rules = Lists.newArrayList();
//        rules.add("R_AGE");
//        rules.add("R_AGE_DAYS");
//        ruleSet.setRefRules(rules);
//
//        FeeSpec fee3 = new FeeSpec();
//        fee3.setName("实际支付保费");
//        fee3.setCode("APP");
//        fee3.setBizCate(FeeBizCate.PREMIUM);
//        fee3.setAsPremium(true);
//        fee3.setComposeFrom(FeeComposeFrom.FORMULA);
//        product.getSubComponents().add(fee3);
//
//        FormulaSpec formula6 = new FormulaSpec();
//        formula6.setName("实际支付保费计算公式");
//        formula6.setCode("F_APP");
//        formula6.setPurpose(FormulaPurpose.APP);
//        List<String> factors6 = Lists.newArrayList();
//        factors6.add("SNP");
//        formula6.setFactors(factors6);
//        formula6.setBody(" x = SNP; return [APP:x]");
//        fee3.getSubComponents().add(formula6);
//
//        FeeSpec fee4 = new FeeSpec();
//        fee4.setName("毛保费");
//        fee4.setCode("SGP");
//        fee4.setBizCate(FeeBizCate.PREMIUM);
//        fee4.setAsPremium(true);
//        fee4.setComposeFrom(FeeComposeFrom.COVERAGE);
//        product.getSubComponents().add(fee4);
//
//        FeeSpec fee5 = new FeeSpec();
//        fee5.setName("净保费");
//        fee5.setCode("SNP");
//        fee5.setBizCate(FeeBizCate.PREMIUM);
//        fee5.setAsPremium(true);
//        fee5.setComposeFrom(FeeComposeFrom.COVERAGE);
//        product.getSubComponents().add(fee5);
//
//        EndorsementSpec endo = new EndorsementSpec();
//        endo.setCode("CANCELLATION_FROM_MIDWAY");
//        endo.setName("中途退保");
//        endo.setEndorsementType(EndorsementType.CANCELLATION);
//        product.getSubComponents().add(endo);
//
//        FormulaSpec formula7 = new FormulaSpec();
//        formula7.setName("中途退保收退费计算公式");
//        formula7.setCode("F_CANCELLATION_FROM_MIDWAY");
//        formula7.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors7 = Lists.newArrayList();
//        factors7.add("ORIGINAL_PREMIUM");
//        factors7.add("ENDO_PRO_RATE");
//        formula7.setFactors(factors7);
//        formula7.setBody(" x = ORIGINAL_PREMIUM * ENDO_PRO_RATE * 0.65; return [SNP:x]");
//        endo.getSubComponents().add(formula7);
//
//        FormulaSpec formula10 = new FormulaSpec();
//        formula10.setName("实际支付保费计算公式");
//        formula10.setCode("F_APP");
//        formula10.setPurpose(FormulaPurpose.APP);
//        List<String> factors10 = Lists.newArrayList();
//        factors10.add("SNP");
//        formula10.setFactors(factors10);
//        formula10.setBody(" x = SNP; return [APP:x]");
//        endo.getSubComponents().add(formula10);
//
//        return product;
//    }
//
//    private ProductSpec initTaxFreeProduct(){
//        ProductSpec product = new ProductSpec();
//        product.setName("民生惠康保个人税收优惠型健康保险A款（万能型 ）");
//        product.setCode("114801");
//        product.setOrganization("310000");
//        product.setEffectiveDate(new DateTime(2017,7,20,0,0,0).toDate());
//        product.setExpiredDate(new DateTime(2099,12,31,0,0,0).toDate());
//        product.setStatus(ProductStatus.EFFECTIVE);
//        product.setVersion("1.0");
//        product.setFixedCoverage(true);
//
//        LimitSpec limit = new LimitSpec();
//        limit.setIndemnityType(IndemnityType.AOP);
//        limit.setPattern(LimitPartten.FORMULA.getValue());
//        limit.setFixed(true);
//        limit.setDefaultValue("limitAmount:200000.00");
//        product.getSubComponents().add(limit);
//
//        FormulaSpec limitformula = new FormulaSpec();
//        limitformula.setName("税优健康险总保额公式");
//        limitformula.setCode("F_LIMIT_114801");
//        limitformula.setPurpose(FormulaPurpose.LIMIT);
//        List<String> limitfactors = Lists.newArrayList();
//        limitfactors.add("SICK");
//        limitformula.setFactors(limitfactors);
//        limitformula.setBody(" x = 200000.00; if(SICK == '1'){ x = 40000.00;}; return [LIMIT:x]");
//        limit.getSubComponents().add(limitformula);
//
//        InsuredObjectSpec insured = new InsuredObjectSpec();
//        insured.setCate(InsuredObjectCate.PERSON);
//        insured.setIsMultiple(false);
//        product.getSubComponents().add(insured);
//
//        CoverageSpec cover1 = new CoverageSpec();
//        cover1.setName("住院及前后门诊医疗费用");
//        cover1.setCode("07000003");
//        cover1.setPrimary(true);
//        product.getSubComponents().add(cover1);
//
//        LimitSpec limit1 = new LimitSpec();
//        limit1.setIndemnityType(IndemnityType.AOP);
//        limit1.setPattern(LimitPartten.FORMULA.getValue());
//        limit1.setFixed(true);
//        limit1.setDefaultValue("limitAmount:200000.00");
//        cover1.getSubComponents().add(limit1);
//
//        FormulaSpec limit1formula = new FormulaSpec();
//        limit1formula.setName("住院及前后门诊医疗费用保额公式");
//        limit1formula.setCode("F_LIMIT_07000003");
//        limit1formula.setPurpose(FormulaPurpose.LIMIT);
//        List<String> limit1factors = Lists.newArrayList();
//        limit1factors.add("SICK");
//        limit1formula.setFactors(limit1factors);
//        limit1formula.setBody(" x = 200000.00; if(SICK == '1'){ x = 40000.00;}; return [LIMIT:x]");
//        limit1.getSubComponents().add(limit1formula);
//
//        FormulaSpec formula1 = new FormulaSpec();
//        formula1.setName("税优健康险保费公式");
//        formula1.setCode("F_PREMIUM_TAXFREE");
//        formula1.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors1 = Lists.newArrayList();
//        factors1.add("INSURED_AGE");
//        factors1.add("HEALTH_INSURANCE");
//        formula1.setFactors(factors1);
//        formula1.setBody("x =  RateTable('RATE_TAXFREE',['INSURED_AGE':INSURED_AGE,'HEALTH_INSURANCE':HEALTH_INSURANCE]); return ['SGP':x,'SNP':x];");
//        cover1.getSubComponents().add(formula1);
//
//        CoverageSpec cover2 = new CoverageSpec();
//        cover2.setName("特定门诊治疗费用");
//        cover2.setCode("07000004");
//        cover2.setPrimary(true);
//        cover2.setStatus(CoverageStatus.ENABLE);
//        product.getSubComponents().add(cover2);
//
//        LimitSpec limit2= new LimitSpec();
//        limit2.setIndemnityType(IndemnityType.AOP);
//        limit2.setPattern(LimitPartten.FORMULA.getValue());
//        limit2.setFixed(true);
//        limit2.setDefaultValue("limitAmount:20000.00");
//        cover2.getSubComponents().add(limit2);
//
//        FormulaSpec limit2formula = new FormulaSpec();
//        limit2formula.setName("特定门诊治疗费用保额公式");
//        limit2formula.setCode("F_LIMIT_07000004");
//        limit2formula.setPurpose(FormulaPurpose.LIMIT);
//        List<String> limit2factors = Lists.newArrayList();
//        limit2factors.add("SICK");
//        limit2formula.setFactors(limit2factors);
//        limit2formula.setBody(" x = 20000.00; if(SICK == '1'){ x = 5000.00;}; return [LIMIT:x]");
//        limit2.getSubComponents().add(limit2formula);
//
//        FormulaSpec formula2 = new FormulaSpec();
//        formula2.setName("0保费公式");
//        formula2.setCode("F_PREMIUM_ZERO");
//        formula2.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors2 = Lists.newArrayList();
//        formula2.setFactors(factors2);
//        formula2.setBody("x =  0.00; return ['SGP':x,'SNP':x];");
//        cover2.getSubComponents().add(formula2);
//
//        CoverageSpec cover3 = new CoverageSpec();
//        cover2.setName("慢性病门诊治疗费用");
//        cover2.setCode("07000005");
//        cover2.setPrimary(true);
//        cover2.setStatus(CoverageStatus.ENABLE);
//        product.getSubComponents().add(cover3);
//
//        LimitSpec limit3= new LimitSpec();
//        limit3.setIndemnityType(IndemnityType.AOP);
//        limit3.setPattern(LimitPartten.FORMULA.getValue());
//        limit3.setFixed(true);
//        limit3.setDefaultValue("limitAmount:3000.00");
//        cover3.getSubComponents().add(limit3);
//
//        FormulaSpec limit3formula = new FormulaSpec();
//        limit3formula.setName("慢性病门诊治疗费用保额公式");
//        limit3formula.setCode("F_LIMIT_07000005");
//        limit3formula.setPurpose(FormulaPurpose.LIMIT);
//        List<String> limit3factors = Lists.newArrayList();
//        limit3factors.add("SICK");
//        limit3formula.setFactors(limit3factors);
//        limit3formula.setBody(" x = 3000.00; if(SICK == '1'){ x = 1000.00;}; return [LIMIT:x]");
//        limit3.getSubComponents().add(limit3formula);
//
//        FormulaSpec formula3 = new FormulaSpec();
//        formula3.setName("0保费公式");
//        formula3.setCode("F_PREMIUM_ZERO");
//        formula3.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors3 = Lists.newArrayList();
//        formula3.setFactors(factors3);
//        formula3.setBody("x =  0.00; return ['SGP':x,'SNP':x];");
//        cover3.getSubComponents().add(formula3);
//
//        RuleSetSpec ruleSet = new RuleSetSpec();
//        ruleSet.setName("核保");
//        ruleSet.setCode("RS_UW");
//        product.getSubComponents().add(ruleSet);
//
//        RuleSpec rule = new RuleSpec();
//        rule.setName("男性被保险人年龄上限");
//        rule.setCode("R_M_AGE");
//        rule.setType(RuleType.VALIDATION);
//        rule.setBody("INSURED_GENDER == 'M' && INSURED_AGE > 65");
//        List<String> rfactors = Lists.newArrayList();
//        rfactors.add("INSURED_AGE");
//        rfactors.add("INSURED_GENDER");
//        rule.setFactors(rfactors);
//        rule.setMessage("被保险人年龄不能超过法定退休年龄。");
//        product.getSubComponents().add(rule);
//
//        RuleSpec rule2 = new RuleSpec();
//        rule2.setName("女性被保险人年龄上限");
//        rule2.setCode("R_F_AGE");
//        rule2.setType(RuleType.VALIDATION);
//        rule2.setBody("INSURED_GENDER == 'F' && INSURED_AGE > 55");
//        List<String> rfactors2 = Lists.newArrayList();
//        rfactors2.add("INSURED_AGE");
//        rfactors2.add("INSURED_GENDER");
//        rule2.setFactors(rfactors2);
//        rule2.setMessage("被保险人年龄不能超过法定退休年龄。");
//        product.getSubComponents().add(rule2);
//
//        RuleSpec rule3 = new RuleSpec();
//        rule3.setName("被保险人年龄下限");
//        rule3.setCode("R_AGE");
//        rule3.setType(RuleType.VALIDATION);
//        rule3.setBody("INSURED_AGE < 16");
//        List<String> rfactors3 = Lists.newArrayList();
//        rfactors3.add("INSURED_AGE");
//        rule3.setFactors(rfactors3);
//        rule3.setMessage("被保险人年龄不能低于16周岁。");
//        product.getSubComponents().add(rule3);
//
//        List<String> rules = Lists.newArrayList();
//        rules.add("R_AGE");
//        rules.add("R_M_AGE");
//        rules.add("R_F_AGE");
//        ruleSet.setRefRules(rules);
//
//        FeeSpec fee3 = new FeeSpec();
//        fee3.setName("实际支付保费");
//        fee3.setCode("APP");
//        fee3.setBizCate(FeeBizCate.PREMIUM);
//        fee3.setAsPremium(true);
//        fee3.setComposeFrom(FeeComposeFrom.FORMULA);
//        product.getSubComponents().add(fee3);
//
//        FormulaSpec formula6 = new FormulaSpec();
//        formula6.setName("实际支付保费计算公式");
//        formula6.setCode("F_APP");
//        formula6.setPurpose(FormulaPurpose.APP);
//        List<String> factors6 = Lists.newArrayList();
//        factors6.add("SNP");
//        formula6.setFactors(factors6);
//        formula6.setBody(" x = SNP; if(SNP < 2400.00){ x = 2400.00;}; return [APP:x]");
//        fee3.getSubComponents().add(formula6);
//
//        FeeSpec fee4 = new FeeSpec();
//        fee4.setName("毛保费");
//        fee4.setCode("SGP");
//        fee4.setBizCate(FeeBizCate.PREMIUM);
//        fee4.setAsPremium(true);
//        fee4.setComposeFrom(FeeComposeFrom.COVERAGE);
//        product.getSubComponents().add(fee4);
//
//        FeeSpec fee5 = new FeeSpec();
//        fee5.setName("净保费");
//        fee5.setCode("SNP");
//        fee5.setBizCate(FeeBizCate.PREMIUM);
//        fee5.setAsPremium(true);
//        fee5.setComposeFrom(FeeComposeFrom.COVERAGE);
//        product.getSubComponents().add(fee5);
//
//        EndorsementSpec endo = new EndorsementSpec();
//        endo.setCode("CANCELLATION_FROM_MIDWAY");
//        endo.setName("中途退保");
//        endo.setEndorsementType(EndorsementType.CANCELLATION);
//        product.getSubComponents().add(endo);
//
//        FormulaSpec formula7 = new FormulaSpec();
//        formula7.setName("中途退保收退费计算公式");
//        formula7.setCode("F_CANCELLATION_FROM_MIDWAY");
//        formula7.setPurpose(FormulaPurpose.PREMIUM);
//        List<String> factors7 = Lists.newArrayList();
//        factors7.add("ORIGINAL_PREMIUM");
//        factors7.add("ENDO_PRO_RATE");
//        formula7.setFactors(factors7);
//        formula7.setBody(" x = ORIGINAL_PREMIUM * ENDO_PRO_RATE * 0.65; return [SNP:x]");
//        endo.getSubComponents().add(formula7);
//
//        FormulaSpec formula10 = new FormulaSpec();
//        formula10.setName("实际支付保费计算公式");
//        formula10.setCode("F_APP");
//        formula10.setPurpose(FormulaPurpose.APP);
//        List<String> factors10 = Lists.newArrayList();
//        factors10.add("SNP");
//        formula10.setFactors(factors10);
//        formula10.setBody(" x = SNP; return [APP:x]");
//        endo.getSubComponents().add(formula10);
//
//        return product;
//    }



    enum TestEnum{
        A(1,"Y"),
        B(2,"X");

        private int code;
        private String name;

        private TestEnum(int code,String name){
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
