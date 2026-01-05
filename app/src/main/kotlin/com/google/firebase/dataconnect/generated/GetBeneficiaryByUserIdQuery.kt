
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


public interface GetBeneficiaryByUserIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetBeneficiaryByUserIdQuery.Data,
      GetBeneficiaryByUserIdQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val uid: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiaries: List<BeneficiariesItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class BeneficiariesItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: String,
    val studentNumer: Int,
    val nif: String,
    val course: String,
    val isActive: Boolean,
    val contactNumber: String,
    val uid: String?,
    val address: String?,
    val observations: String?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetBeneficiaryByUserId"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetBeneficiaryByUserIdQuery.ref(
  
    uid: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetBeneficiaryByUserIdQuery.Data,
    GetBeneficiaryByUserIdQuery.Variables
  > =
  ref(
    
      GetBeneficiaryByUserIdQuery.Variables(
        uid=uid,
  
      )
    
  )

public suspend fun GetBeneficiaryByUserIdQuery.execute(
  
    uid: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetBeneficiaryByUserIdQuery.Data,
    GetBeneficiaryByUserIdQuery.Variables
  > =
  ref(
    
      uid=uid,
  
    
  ).execute()


  public fun GetBeneficiaryByUserIdQuery.flow(
    
      uid: String,
  
    
    ): kotlinx.coroutines.flow.Flow<GetBeneficiaryByUserIdQuery.Data> =
    ref(
        
          uid=uid,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

