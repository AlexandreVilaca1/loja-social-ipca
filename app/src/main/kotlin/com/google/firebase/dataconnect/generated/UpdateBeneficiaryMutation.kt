
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



public interface UpdateBeneficiaryMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateBeneficiaryMutation.Data,
      UpdateBeneficiaryMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val studentNumer: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val nif: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val course: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val isActive: com.google.firebase.dataconnect.OptionalVariable<Boolean?>,
    val contactNumber: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val address: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var fullName: String?
        public var studentNumer: Int?
        public var nif: String?
        public var course: String?
        public var isActive: Boolean?
        public var contactNumber: String?
        public var address: String?
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var fullName: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var studentNumer: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var nif: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var course: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var isActive: com.google.firebase.dataconnect.OptionalVariable<Boolean?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var contactNumber: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var address: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var fullName: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { fullName = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var studentNumer: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { studentNumer = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var nif: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nif = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var course: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { course = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var isActive: Boolean?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { isActive = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var contactNumber: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { contactNumber = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var address: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { address = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,fullName=fullName,studentNumer=studentNumer,nif=nif,course=course,isActive=isActive,contactNumber=contactNumber,address=address,observations=observations,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiary_update: BeneficiaryKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateBeneficiary"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateBeneficiaryMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateBeneficiaryMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateBeneficiaryMutation.Data,
    UpdateBeneficiaryMutation.Variables
  > =
  ref(
    
      UpdateBeneficiaryMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateBeneficiaryMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateBeneficiaryMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateBeneficiaryMutation.Data,
    UpdateBeneficiaryMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


