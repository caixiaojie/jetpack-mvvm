package com.cxj.jetpackmvvm.base

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/15
 *      des:
 * </pre>
 */
data class Test(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val list: List<X>,
    val total: Int
)

data class X(
    val approvalStatus: Any,
    val approvalTaskCode: String,
    val attributes: String,
    val baseKnowledgeId: List<String>,
    val baseKnowledgeNodeId: String,
    val baseKnowledgeNodeIdName: Any,
    val chargePerson: String,
    val chargePersonName: String,
    val createAt: String,
    val createByName: String,
    val designateStatus: String,
    val designateTaskCode: String,
    val endTime: String,
    val executionFrequency: String,
    val id: String,
    val isCharge: String,
    val isMember: String,
    val isRecheck: String,
    val member: String,
    val memberName: String,
    val memberReadyStatus: List<MemberReadyStatu>,
    val programExecutionDepartment: String,
    val receiptPicture: List<String>,
    val recheckPerson: String,
    val recheckPersonName: String,
    val recheckReason: Any,
    val recheckStatus: String,
    val relatedEvent: String,
    val relatedEventType: String,
    val sendDepartment: String,
    val sendPerson: String,
    val sendReasons: String,
    val sendTime: String,
    val startTime: String,
    val taskEvaluate: Any,
    val taskIsReady: Int,
    val taskRemark: String,
    val taskSource: String,
    val taskStatus: String,
    val taskType: String,
    val workLocation: String,
    val workReceipt: String,
    val workTaskCode: String,
    val workType: String
)

data class MemberReadyStatu(
    val id: String,
    val isCharge: String,
    val isMember: String,
    val isRecheck: String,
    val memberCode: String,
    val memberName: String,
    val prepareStatus: Int,
    val workTaskCode: String
)