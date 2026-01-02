
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



public interface UpdateLotQuantityMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateLotQuantityMutation.Data,
      UpdateLotQuantityMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val currentQuantity: Int
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val lot_update: LotKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateLotQuantity"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateLotQuantityMutation.ref(
  
    id: java.util.UUID,currentQuantity: Int,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateLotQuantityMutation.Data,
    UpdateLotQuantityMutation.Variables
  > =
  ref(
    
      UpdateLotQuantityMutation.Variables(
        id=id,currentQuantity=currentQuantity,
  
      )
    
  )

public suspend fun UpdateLotQuantityMutation.execute(
  
    id: java.util.UUID,currentQuantity: Int,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateLotQuantityMutation.Data,
    UpdateLotQuantityMutation.Variables
  > =
  ref(
    
      id=id,currentQuantity=currentQuantity,
  
    
  ).execute()


