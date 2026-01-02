
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



public interface CreateBeneficiaryMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateBeneficiaryMutation.Data,
      CreateBeneficiaryMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val fullName: String,
    val studentNumer: Int,
    val nif: String,
    val course: String,
    val isActive: Boolean,
    val contactNumber: String,
    val address: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var fullName: String
        public var studentNumer: Int
        public var nif: String
        public var course: String
        public var isActive: Boolean
        public var contactNumber: String
        public var address: String?
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          fullName: String,studentNumer: Int,nif: String,course: String,isActive: Boolean,contactNumber: String,
          block_: Builder.() -> Unit
        ): Variables {
          var fullName= fullName
            var studentNumer= studentNumer
            var nif= nif
            var course= course
            var isActive= isActive
            var contactNumber= contactNumber
            var address: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var fullName: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { fullName = value_ }
              
            override var studentNumer: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { studentNumer = value_ }
              
            override var nif: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nif = value_ }
              
            override var course: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { course = value_ }
              
            override var isActive: Boolean
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { isActive = value_ }
              
            override var contactNumber: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { contactNumber = value_ }
              
            override var address: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { address = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              fullName=fullName,studentNumer=studentNumer,nif=nif,course=course,isActive=isActive,contactNumber=contactNumber,address=address,observations=observations,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiary_insert: BeneficiaryKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateBeneficiary"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateBeneficiaryMutation.ref(
  
    fullName: String,studentNumer: Int,nif: String,course: String,isActive: Boolean,contactNumber: String,
  
    block_: CreateBeneficiaryMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateBeneficiaryMutation.Data,
    CreateBeneficiaryMutation.Variables
  > =
  ref(
    
      CreateBeneficiaryMutation.Variables.build(
        fullName=fullName,studentNumer=studentNumer,nif=nif,course=course,isActive=isActive,contactNumber=contactNumber,
  
    block_
      )
    
  )

public suspend fun CreateBeneficiaryMutation.execute(
  
    fullName: String,studentNumer: Int,nif: String,course: String,isActive: Boolean,contactNumber: String,
  
    block_: CreateBeneficiaryMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateBeneficiaryMutation.Data,
    CreateBeneficiaryMutation.Variables
  > =
  ref(
    
      fullName=fullName,studentNumer=studentNumer,nif=nif,course=course,isActive=isActive,contactNumber=contactNumber,
  
    block_
    
  ).execute()


