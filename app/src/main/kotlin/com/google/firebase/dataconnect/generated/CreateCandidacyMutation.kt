
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



public interface CreateCandidacyMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateCandidacyMutation.Data,
      CreateCandidacyMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val applicationDate: com.google.firebase.dataconnect.LocalDate,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<@kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var applicationDate: com.google.firebase.dataconnect.LocalDate
        public var statusId: java.util.UUID?
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          applicationDate: com.google.firebase.dataconnect.LocalDate,
          block_: Builder.() -> Unit
        ): Variables {
          var applicationDate= applicationDate
            var statusId: com.google.firebase.dataconnect.OptionalVariable<java.util.UUID?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var applicationDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { applicationDate = value_ }
              
            override var statusId: java.util.UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              applicationDate=applicationDate,statusId=statusId,observations=observations,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val candidacy_insert: CandidacyKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateCandidacy"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateCandidacyMutation.ref(
  
    applicationDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateCandidacyMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateCandidacyMutation.Data,
    CreateCandidacyMutation.Variables
  > =
  ref(
    
      CreateCandidacyMutation.Variables.build(
        applicationDate=applicationDate,
  
    block_
      )
    
  )

public suspend fun CreateCandidacyMutation.execute(
  
    applicationDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateCandidacyMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateCandidacyMutation.Data,
    CreateCandidacyMutation.Variables
  > =
  ref(
    
      applicationDate=applicationDate,
  
    block_
    
  ).execute()


