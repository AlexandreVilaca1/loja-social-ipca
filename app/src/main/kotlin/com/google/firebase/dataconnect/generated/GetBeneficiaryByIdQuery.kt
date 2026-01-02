
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


public interface GetBeneficiaryByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetBeneficiaryByIdQuery.Data,
      GetBeneficiaryByIdQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiary: Beneficiary?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Beneficiary(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: String,
    val studentNumer: Int,
    val nif: String,
    val course: String,
    val isActive: Boolean,
    val contactNumber: String,
    val address: String?,
    val observations: String?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetBeneficiaryById"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetBeneficiaryByIdQuery.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetBeneficiaryByIdQuery.Data,
    GetBeneficiaryByIdQuery.Variables
  > =
  ref(
    
      GetBeneficiaryByIdQuery.Variables(
        id=id,
  
      )
    
  )

public suspend fun GetBeneficiaryByIdQuery.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetBeneficiaryByIdQuery.Data,
    GetBeneficiaryByIdQuery.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


  public fun GetBeneficiaryByIdQuery.flow(
    
      id: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<GetBeneficiaryByIdQuery.Data> =
    ref(
        
          id=id,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

