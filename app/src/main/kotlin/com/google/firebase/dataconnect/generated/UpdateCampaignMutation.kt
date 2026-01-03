
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



public interface UpdateCampaignMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateCampaignMutation.Data,
      UpdateCampaignMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val campaignName: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val campaignStartDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val campaignEndDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val address: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val type: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var campaignName: String?
        public var campaignStartDate: com.google.firebase.dataconnect.LocalDate?
        public var campaignEndDate: com.google.firebase.dataconnect.LocalDate?
        public var address: String?
        public var type: String?
        public var observations: String?
        public var userId: UserKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var campaignName: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var campaignStartDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var campaignEndDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var address: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var type: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var campaignName: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { campaignName = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var campaignStartDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { campaignStartDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var campaignEndDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { campaignEndDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var address: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { address = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var type: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { type = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var userId: UserKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { userId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var statusId: StatusTypeKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,campaignName=campaignName,campaignStartDate=campaignStartDate,campaignEndDate=campaignEndDate,address=address,type=type,observations=observations,userId=userId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val campaign_update: CampaignKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateCampaign"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateCampaignMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateCampaignMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateCampaignMutation.Data,
    UpdateCampaignMutation.Variables
  > =
  ref(
    
      UpdateCampaignMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateCampaignMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateCampaignMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateCampaignMutation.Data,
    UpdateCampaignMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


