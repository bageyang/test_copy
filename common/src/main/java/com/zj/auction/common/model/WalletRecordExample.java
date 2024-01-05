package com.zj.auction.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WalletRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WalletRecordExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andWalletTypeIsNull() {
            addCriterion("wallet_type is null");
            return (Criteria) this;
        }

        public Criteria andWalletTypeIsNotNull() {
            addCriterion("wallet_type is not null");
            return (Criteria) this;
        }

        public Criteria andWalletTypeEqualTo(Byte value) {
            addCriterion("wallet_type =", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeNotEqualTo(Byte value) {
            addCriterion("wallet_type <>", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeGreaterThan(Byte value) {
            addCriterion("wallet_type >", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("wallet_type >=", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeLessThan(Byte value) {
            addCriterion("wallet_type <", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeLessThanOrEqualTo(Byte value) {
            addCriterion("wallet_type <=", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeIn(List<Byte> values) {
            addCriterion("wallet_type in", values, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeNotIn(List<Byte> values) {
            addCriterion("wallet_type not in", values, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeBetween(Byte value1, Byte value2) {
            addCriterion("wallet_type between", value1, value2, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("wallet_type not between", value1, value2, "walletType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeIsNull() {
            addCriterion("transaction_type is null");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeIsNotNull() {
            addCriterion("transaction_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeEqualTo(Byte value) {
            addCriterion("transaction_type =", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeNotEqualTo(Byte value) {
            addCriterion("transaction_type <>", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeGreaterThan(Byte value) {
            addCriterion("transaction_type >", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("transaction_type >=", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeLessThan(Byte value) {
            addCriterion("transaction_type <", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeLessThanOrEqualTo(Byte value) {
            addCriterion("transaction_type <=", value, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeIn(List<Byte> values) {
            addCriterion("transaction_type in", values, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeNotIn(List<Byte> values) {
            addCriterion("transaction_type not in", values, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeBetween(Byte value1, Byte value2) {
            addCriterion("transaction_type between", value1, value2, "transactionType");
            return (Criteria) this;
        }

        public Criteria andTransactionTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("transaction_type not between", value1, value2, "transactionType");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeIsNull() {
            addCriterion("balance_before is null");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeIsNotNull() {
            addCriterion("balance_before is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeEqualTo(BigDecimal value) {
            addCriterion("balance_before =", value, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeNotEqualTo(BigDecimal value) {
            addCriterion("balance_before <>", value, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeGreaterThan(BigDecimal value) {
            addCriterion("balance_before >", value, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_before >=", value, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeLessThan(BigDecimal value) {
            addCriterion("balance_before <", value, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_before <=", value, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeIn(List<BigDecimal> values) {
            addCriterion("balance_before in", values, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeNotIn(List<BigDecimal> values) {
            addCriterion("balance_before not in", values, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_before between", value1, value2, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andBalanceBeforeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_before not between", value1, value2, "balanceBefore");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceIsNull() {
            addCriterion("change_balance is null");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceIsNotNull() {
            addCriterion("change_balance is not null");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceEqualTo(BigDecimal value) {
            addCriterion("change_balance =", value, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceNotEqualTo(BigDecimal value) {
            addCriterion("change_balance <>", value, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceGreaterThan(BigDecimal value) {
            addCriterion("change_balance >", value, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("change_balance >=", value, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceLessThan(BigDecimal value) {
            addCriterion("change_balance <", value, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("change_balance <=", value, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceIn(List<BigDecimal> values) {
            addCriterion("change_balance in", values, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceNotIn(List<BigDecimal> values) {
            addCriterion("change_balance not in", values, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("change_balance between", value1, value2, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andChangeBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("change_balance not between", value1, value2, "changeBalance");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterIsNull() {
            addCriterion("balance_after is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterIsNotNull() {
            addCriterion("balance_after is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterEqualTo(BigDecimal value) {
            addCriterion("balance_after =", value, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterNotEqualTo(BigDecimal value) {
            addCriterion("balance_after <>", value, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterGreaterThan(BigDecimal value) {
            addCriterion("balance_after >", value, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_after >=", value, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterLessThan(BigDecimal value) {
            addCriterion("balance_after <", value, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_after <=", value, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterIn(List<BigDecimal> values) {
            addCriterion("balance_after in", values, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterNotIn(List<BigDecimal> values) {
            addCriterion("balance_after not in", values, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_after between", value1, value2, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andBalanceAfterNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_after not between", value1, value2, "balanceAfter");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}