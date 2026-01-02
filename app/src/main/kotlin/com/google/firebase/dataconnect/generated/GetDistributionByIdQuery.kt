
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


public interface GetDistributionByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetDistributionByIdQuery.Data,
      GetDistributionByIdQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val distribution: Distribution?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Distribution(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val distributionDate: com.google.firebase.dataconnect.LocalDate,
    val observations: String?,
    val responsibleStaff: ResponsibleStaff?,
    val beneficiary: Beneficiary?,
    val status: Status?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class ResponsibleStaff(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val email: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Beneficiary(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: String,
    val studentNumer: Int,
    val contactNumber: String
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
    public val operationName: String = "GetDistributionById"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetDistributionByIdQuery.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetDistributionByIdQuery.Data,
    GetDistributionByIdQuery.Variables
  > =
  ref(
    
      GetDistributionByIdQuery.Variables(
        id=id,
  
      )
    
  )

public suspend fun GetDistributionByIdQuery.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetDistributionByIdQuery.Data,
    GetDistributionByIdQuery.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


  public fun GetDistributionByIdQuery.flow(
    
      id: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<GetDistributionByIdQuery.Data> =
    ref(
        
          id=id,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

