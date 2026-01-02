
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



public interface DeleteDistributionItemMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteDistributionItemMutation.Data,
      DeleteDistributionItemMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val distributionId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val lotId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val distributionItem_delete: DistributionItemKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteDistributionItem"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteDistributionItemMutation.ref(
  
    distributionId: java.util.UUID,lotId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteDistributionItemMutation.Data,
    DeleteDistributionItemMutation.Variables
  > =
  ref(
    
      DeleteDistributionItemMutation.Variables(
        distributionId=distributionId,lotId=lotId,
  
      )
    
  )

public suspend fun DeleteDistributionItemMutation.execute(
  
    distributionId: java.util.UUID,lotId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteDistributionItemMutation.Data,
    DeleteDistributionItemMutation.Variables
  > =
  ref(
    
      distributionId=distributionId,lotId=lotId,
  
    
  ).execute()


