package rf.policyadmin.model.trans.impl;

import com.google.common.collect.Lists;
import org.joda.time.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.CertificateInfo;
import rf.foundation.utils.CertificateNoUtils;
import rf.foundation.utils.CloneUtils;
import rf.policyadmin.model.*;
import rf.policyadmin.model.enums.IDType;
import rf.policyadmin.model.trans.PolicyTransformer;

import java.util.Date;
import java.util.List;


@Service
public class PolicyTransformerImpl implements PolicyTransformer {

    @Override
    public Policy transFromQuotation(Quotation quotation) {
        Policy policy = new Policy();
        //clone a new quotation object to prevent the object reference
//        quotation = CloneUtils.clone(quotation);
        //calculate poi
        if(quotation.getExpiredDate() == null){
            Date expDate = new LocalDateTime(quotation.getEffectiveDate()).plusYears(1).plusSeconds(-1).toDate();
        }

        DateTime expDate = new DateTime(quotation.getExpiredDate());
        if(expDate.getHourOfDay() == 0 && expDate.getMinuteOfHour() == 0 && expDate.getSecondOfMinute() == 0){
            expDate = expDate.plusDays(1).plusSeconds(-1);
            quotation.setExpiredDate(expDate.toDate());
        }

        if(quotation.getPoi() == null){
            Days days = Days.daysBetween(new LocalDateTime(quotation.getEffectiveDate()), new LocalDateTime(quotation.getExpiredDate()));
            quotation.setPoi(days.getDays() + 1);
        }

        List<Customer> Customers = quotation.getInsureds();
        Customers.forEach(cust -> {
            if(cust instanceof PersonCustomer && cust.getIdType() != null && cust.getIdNumber() != null && cust.getIdType().equals(IDType.ID.name()) ){
                CertificateInfo idInfo = CertificateNoUtils.paseCertificateNo(cust.getIdNumber());
                if(idInfo.getErrorMessage() == null) {
                    ((PersonCustomer)cust).setBirthday(idInfo.getBirthday());
                    ((PersonCustomer)cust).setGender(idInfo.getSex());

                    if(idInfo.getBirthday() != null){
                        Years years = Years.yearsBetween(new LocalDate(idInfo.getBirthday()), new LocalDate(new Date()));
                        cust.getDynamicFields().put("INSURED_AGE",years.getYears());
                    }
                }else {
                    throw new GenericException(30008L);
                }
            }
        });

        Customer policyHolder = quotation.getPolicyHolder();
        if( policyHolder != null && policyHolder.getIdType() != null && policyHolder.getIdNumber() != null && policyHolder.getIdType().equals(IDType.ID.name()) ){
            CertificateInfo idInfo = CertificateNoUtils.paseCertificateNo(policyHolder.getIdNumber());
            if(idInfo.getErrorMessage() == null) {
                ((PersonCustomer)policyHolder).setBirthday(idInfo.getBirthday());
                ((PersonCustomer)policyHolder).setGender(idInfo.getSex());
                if(idInfo.getBirthday() != null){
                    Years years = Years.yearsBetween(new LocalDate(idInfo.getBirthday()), new LocalDate(new Date()));
                    policyHolder.getDynamicFields().put("HOLDER_AGE",years.getYears());
                }
            }else {
                throw new GenericException(30008L);
            }
        }

        try {
            String uuid = policy.getUuid();
            BeanUtils.copyProperties(quotation, policy);
            policy.setUuid(uuid);
            policy.setSubComponents(Lists.newArrayList());

            transform(quotation, policy);

        }catch (Exception e){
            throw new GenericException(e);
        }
        return policy;
    }

    @Override
    public Policy transFromQuotation(Quotation quotation,Policy policy){
        //clone a new quotation object to prevent the object reference
//        quotation = CloneUtils.clone(quotation);
        //calculate poi
        if(quotation.getPoi() == null){
            Days days = Days.daysBetween(new LocalDate(quotation.getEffectiveDate()), new LocalDate(quotation.getExpiredDate()));
            quotation.setPoi(days.getDays());
        }

        try {
            String uuid = policy.getUuid();
            BeanUtils.copyProperties(quotation, policy);
            policy.setUuid(String.valueOf(uuid));
            policy.setSubComponents(Lists.newArrayList());

            transform(quotation, policy);

        }catch (Exception e){
            e.printStackTrace();
            throw new GenericException(e);
        }
        return policy;
    }

    private void transform(Quotation quotation, Policy policy) {
        policy.setPolicyHolder(quotation.getPolicyHolder());
        policy.setInsureds(quotation.getInsureds());

        List<InsuredObject> subjects = quotation.getSubComponentsByType(InsuredObject.class);
        List<Coverage> coverages = quotation.getSubComponentsByType(Coverage.class);
        if(subjects.size() > 0){
            if(coverages.size() > 0){
                subjects.forEach(insuranceSubject -> {
                    coverages.forEach(coverage -> {
                        insuranceSubject.getSubComponents().add(CloneUtils.clone(coverage));
                    });
                });
            }
            policy.getSubComponents().addAll(subjects);
        }else {
            List<InsuredObject> subjectInstances = Lists.newArrayList();
             policy.getInsureds().forEach( customer ->  {
                if (customer instanceof PersonCustomer) {
                    Person person = new Person();
                    person.setDynamicFields(customer.getDynamicFields());
                    person.setName(customer.getName());
                    person.setIdType(customer.getIdType());
                    person.setIdNumber(customer.getIdNumber());
                    person.setGender(((PersonCustomer) customer).getGender());
                    person.setBirthday(((PersonCustomer) customer).getBirthday());

                    person.setSubComponents(Lists.newArrayList());
                    coverages.forEach(coverage -> {
                        person.getSubComponents().add(CloneUtils.clone(coverage));
                    });
                    subjectInstances.add(person);
                }
            });
            policy.getSubComponents().addAll(subjectInstances);
        }
    }

}
