package rf.customer.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rf.customer.ds.CustomerNumberService;
import rf.customer.ds.CustomerService;
import rf.customer.model.Customer;
import rf.customer.model.QueryCondition;
import rf.customer.repository.CustomerDao;
import rf.customer.repository.pojo.TCustormer;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static java.util.stream.Collectors.toList;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerNumberService customerNumberService;

    @Override
    public String generateCustomer(Customer customer) {

        TCustormer po = customerDao.findByIdNumber(customer.getIdType(),customer.getIdNumber());
        if(po == null) {

            String code = customerNumberService.generateCustomerCode(customer);
            customer.setCode(code);

            po = new TCustormer();
        }

        if(po.getUuid() == null)
            po.setUuid(customer.getUuid());
        po.setName(customer.getName());
        po.setIdType(customer.getIdType());
        po.setIdNumber(customer.getIdNumber());
        po.setCode(customer.getCode());
        po.setPhone(customer.getPhone());

        String content = jsonHelper.toJSON(customer);
        po.setContent(content);

        customerDao.save(po);

        return customer.getCode();
    }

    @Override
    public Customer loadById(String idType, String idNumber) {

        TCustormer po = customerDao.findByIdNumber(idType,idNumber);
        String content = po.getContent();

        Customer customer = jsonHelper.fromJSON(content,Customer.class);

        return customer;
    }

    @Override
    public Customer loadByCode(String code) {
        TCustormer po = customerDao.loadByCode(code);
        String content = po.getContent();

        Customer customer = jsonHelper.fromJSON(content,Customer.class);

        return customer;
    }

    @Override
    public ResponsePage<Customer> findCustomers(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TCustormer> queryCondition = new Specification<TCustormer>() {
            @Override
            public Predicate toPredicate(Root<TCustormer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if (condition.getIdType()!=null&&!"".equals(condition.getIdType())){
                    predicateList.add(criteriaBuilder.equal(root.get("idType"),condition.getIdType()));
                }
                if (condition.getIdNumber()!=null&&!"".equals(condition.getIdNumber())){
                    predicateList.add(criteriaBuilder.equal(root.get("idNumber"),condition.getIdNumber()));
                }
                if (condition.getCode()!=null&&!"".equals(condition.getCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("code"),condition.getCode()));
                }
                if (condition.getName()!=null&&!"".equals(condition.getName())){
                    predicateList.add(criteriaBuilder.equal(root.get("name"),"%" + condition.getName() + "%"));
                }
                if (condition.getPhone()!=null&&!"".equals(condition.getPhone())){
                    predicateList.add(criteriaBuilder.equal(root.get("phone"),condition.getPhone()));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TCustormer> page = customerDao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "creationTime")));

        return buildResponsePage(page);
    }

    private ResponsePage<Customer> buildResponsePage(Page<TCustormer> page){
        List<Customer> datas = page.getContent().stream()
                .map(po -> {
                    Customer customer = jsonHelper.fromJSON(po.getContent(), Customer.class);
                    return customer;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }
}
