package rf.rating;


import org.springframework.stereotype.Service;
import rf.rating.model.enums.FormulaPurpose;

@Service
public class RatingEngine {

    public RatingJob newbizPremium(){

        RatingJob job = new RatingJob();

        AccumulationStrategy limitStrategy = new AccumulationStrategy(new LimitAccumulator());

        AccumulationStrategy premiumStrategy = new AccumulationStrategy(new DefaultAccumulator());

        RatingStage limitStage = new RatingStage(limitStrategy,new LimitCalculator(), FormulaPurpose.LIMIT.name());

        RatingStage premiumStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.PREMIUM.name());

        RatingStage taxfeeStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.TAX_FEE.name());

        RatingStage commissionStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.COMMISSION.name());

        RatingStage appStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.APP.name());

        job.addStage(limitStage)
                .addStage(premiumStage)
                .addStage(taxfeeStage)
                .addStage(commissionStage)
                .addStage(appStage);

        return job;
    }

    public RatingJob endosementPremium() {

        RatingJob job = new RatingJob();

        AccumulationStrategy premiumStrategy = new AccumulationStrategy(new DefaultAccumulator());

        RatingStage premiumStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.PREMIUM.name());

        RatingStage taxfeeStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.TAX_FEE.name());

        RatingStage commissionStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.COMMISSION.name());

        RatingStage appStage = new RatingStage(premiumStrategy,new FormulaCalculator(),FormulaPurpose.APP.name());

        job.addStage(premiumStage)
                .addStage(taxfeeStage)
                .addStage(commissionStage)
                .addStage(appStage);

        return job;
    }

    public RatingJob ruleJob() {
        RatingJob job = new RatingJob();

        MergeStrategy mergeStrategy = new MergeStrategy();

        RatingStage ruleStage = new RatingStage(mergeStrategy,new RuleCalculator(),FormulaPurpose.RULE.name());

        job.addStage(ruleStage);

        return job;
    }
}
