
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



public interface DeleteProductMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteProductMutation.Data,
      DeleteProductMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val product_delete: ProductKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteProduct"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteProductMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteProductMutation.Data,
    DeleteProductMutation.Variables
  > =
  ref(
    
      DeleteProductMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun DeleteProductMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteProductMutation.Data,
    DeleteProductMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


