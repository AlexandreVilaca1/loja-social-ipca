
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



public interface CreateRequestMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateRequestMutation.Data,
      CreateRequestMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val beneficiaryId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val requestDate: com.google.firebase.dataconnect.LocalDate,
    val description: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<@kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var beneficiaryId: java.util.UUID
        public var requestDate: com.google.firebase.dataconnect.LocalDate
        public var description: String?
        public var statusId: java.util.UUID?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          beneficiaryId: java.util.UUID,requestDate: com.google.firebase.dataconnect.LocalDate,
          block_: Builder.() -> Unit
        ): Variables {
          var beneficiaryId= beneficiaryId
            var requestDate= requestDate
            var description: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<java.util.UUID?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var beneficiaryId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { beneficiaryId = value_ }
              
            override var requestDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { requestDate = value_ }
              
            override var description: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { description = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var statusId: java.util.UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              beneficiaryId=beneficiaryId,requestDate=requestDate,description=description,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val request_insert: RequestKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateRequest"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateRequestMutation.ref(
  
    beneficiaryId: java.util.UUID,requestDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateRequestMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateRequestMutation.Data,
    CreateRequestMutation.Variables
  > =
  ref(
    
      CreateRequestMutation.Variables.build(
        beneficiaryId=beneficiaryId,requestDate=requestDate,
  
    block_
      )
    
  )

public suspend fun CreateRequestMutation.execute(
  
    beneficiaryId: java.util.UUID,requestDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateRequestMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateRequestMutation.Data,
    CreateRequestMutation.Variables
  > =
  ref(
    
      beneficiaryId=beneficiaryId,requestDate=requestDate,
  
    block_
    
  ).execute()


