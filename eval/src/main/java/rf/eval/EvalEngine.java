package rf.eval;


import org.springframework.stereotype.Service;
import rf.eval.model.enums.FormulaPurpose;

@Service
public class EvalEngine {

    public EvalJob newbizPremium(){

        EvalJob job = new EvalJob();

        AccumulationStrategy limitStrategy = new AccumulationStrategy(new LimitAccumulator());

        AccumulationStrategy premiumStrategy = new AccumulationStrategy(new DefaultAccumulator());

        EvalStage limitStage = new EvalStage(limitStrategy,new LimitEvaluator(), FormulaPurpose.LIMIT.name());

        EvalStage premiumStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.PREMIUM.name());

        EvalStage taxfeeStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.TAX_FEE.name());

        EvalStage commissionStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.COMMISSION.name());

        EvalStage appStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.APP.name());

        job.addStage(limitStage)
                .addStage(premiumStage)
                .addStage(taxfeeStage)
                .addStage(commissionStage)
                .addStage(appStage);

        return job;
    }

    public EvalJob endosementPremium() {

        EvalJob job = new EvalJob();

        AccumulationStrategy premiumStrategy = new AccumulationStrategy(new DefaultAccumulator());

        EvalStage premiumStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.PREMIUM.name());

        EvalStage taxfeeStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.TAX_FEE.name());

        EvalStage commissionStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.COMMISSION.name());

        EvalStage appStage = new EvalStage(premiumStrategy,new FormulaEvaluator(),FormulaPurpose.APP.name());

        job.addStage(premiumStage)
                .addStage(taxfeeStage)
                .addStage(commissionStage)
                .addStage(appStage);

        return job;
    }

    public EvalJob ruleJob() {
        EvalJob job = new EvalJob();

        MergeStrategy mergeStrategy = new MergeStrategy();

        EvalStage ruleStage = new EvalStage(mergeStrategy,new RuleEvaluator(),FormulaPurpose.RULE.name());

        job.addStage(ruleStage);

        return job;
    }
}
