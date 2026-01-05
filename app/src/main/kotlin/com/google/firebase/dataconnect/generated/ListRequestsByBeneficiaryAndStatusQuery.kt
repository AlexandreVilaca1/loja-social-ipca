
@file:kotlin.Suppress(
  "KotlinRedundantDiagnosticSuppress",
  "LocalVariableName",
  "MayBeConstant",
  "RedundantVisibilityModifier",
  "RemoveEmptyClassBody",
  "SpellCheckingInspection",
  "LocalVariableName",
  "unused",
)

package com.google.firebase.dataconnect.generated


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface ListRequestsByBeneficiaryAndStatusQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListRequestsByBeneficiaryAndStatusQuery.Data,
      ListRequestsByBeneficiaryAndStatusQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val beneficiaryId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val statusCode: String,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var beneficiaryId: java.util.UUID
        public var statusCode: String
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          beneficiaryId: java.util.UUID,statusCode: String,
          block_: Builder.() -> Unit
        ): Variables {
          var beneficiaryId= beneficiaryId
            var statusCode= statusCode
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var beneficiaryId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { beneficiaryId = value_ }
              
            override var statusCode: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusCode = value_ }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              beneficiaryId=beneficiaryId,statusCode=statusCode,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val requests: List<RequestsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class RequestsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val beneficiary: Beneficiary,
    val requestDate: com.google.firebase.dataconnect.LocalDate,
    val description: String?,
    val status: Status?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Beneficiary(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: String,
    val studentNumer: Int
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Status(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val code: String,
    val description: String,
    val color: String?
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListRequestsByBeneficiaryAndStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListRequestsByBeneficiaryAndStatusQuery.ref(
  
    beneficiaryId: java.util.UUID,statusCode: String,
  
    block_: ListRequestsByBeneficiaryAndStatusQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListRequestsByBeneficiaryAndStatusQuery.Data,
    ListRequestsByBeneficiaryAndStatusQuery.Variables
  > =
  ref(
    
      ListRequestsByBeneficiaryAndStatusQuery.Variables.build(
        beneficiaryId=beneficiaryId,statusCode=statusCode,
  
    block_
      )
    
  )

public suspend fun ListRequestsByBeneficiaryAndStatusQuery.execute(
  
    beneficiaryId: java.util.UUID,statusCode: String,
  
    block_: ListRequestsByBeneficiaryAndStatusQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListRequestsByBeneficiaryAndStatusQuery.Data,
    ListRequestsByBeneficiaryAndStatusQuery.Variables
  > =
  ref(
    
      beneficiaryId=beneficiaryId,statusCode=statusCode,
  
    block_
    
  ).execute()


  public fun ListRequestsByBeneficiaryAndStatusQuery.flow(
    
      beneficiaryId: java.util.UUID,statusCode: String,
  
    block_: ListRequestsByBeneficiaryAndStatusQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListRequestsByBeneficiaryAndStatusQuery.Data> =
    ref(
        
          beneficiaryId=beneficiaryId,statusCode=statusCode,
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

