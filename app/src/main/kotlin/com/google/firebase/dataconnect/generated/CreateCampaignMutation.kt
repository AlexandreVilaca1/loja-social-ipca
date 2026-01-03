
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



public interface CreateCampaignMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateCampaignMutation.Data,
      CreateCampaignMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val campaignName: String,
    val campaignStartDate: com.google.firebase.dataconnect.LocalDate,
    val campaignEndDate: com.google.firebase.dataconnect.LocalDate,
    val address: String,
    val type: String,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var campaignName: String
        public var campaignStartDate: com.google.firebase.dataconnect.LocalDate
        public var campaignEndDate: com.google.firebase.dataconnect.LocalDate
        public var address: String
        public var type: String
        public var observations: String?
        public var userId: UserKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          campaignName: String,campaignStartDate: com.google.firebase.dataconnect.LocalDate,campaignEndDate: com.google.firebase.dataconnect.LocalDate,address: String,type: String,
          block_: Builder.() -> Unit
        ): Variables {
          var campaignName= campaignName
            var campaignStartDate= campaignStartDate
            var campaignEndDate= campaignEndDate
            var address= address
            var type= type
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var campaignName: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { campaignName = value_ }
              
            override var campaignStartDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { campaignStartDate = value_ }
              
            override var campaignEndDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { campaignEndDate = value_ }
              
            override var address: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { address = value_ }
              
            override var type: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { type = value_ }
              
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
              campaignName=campaignName,campaignStartDate=campaignStartDate,campaignEndDate=campaignEndDate,address=address,type=type,observations=observations,userId=userId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val campaign_insert: CampaignKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateCampaign"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateCampaignMutation.ref(
  
    campaignName: String,campaignStartDate: com.google.firebase.dataconnect.LocalDate,campaignEndDate: com.google.firebase.dataconnect.LocalDate,address: String,type: String,
  
    block_: CreateCampaignMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateCampaignMutation.Data,
    CreateCampaignMutation.Variables
  > =
  ref(
    
      CreateCampaignMutation.Variables.build(
        campaignName=campaignName,campaignStartDate=campaignStartDate,campaignEndDate=campaignEndDate,address=address,type=type,
  
    block_
      )
    
  )

public suspend fun CreateCampaignMutation.execute(
  
    campaignName: String,campaignStartDate: com.google.firebase.dataconnect.LocalDate,campaignEndDate: com.google.firebase.dataconnect.LocalDate,address: String,type: String,
  
    block_: CreateCampaignMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateCampaignMutation.Data,
    CreateCampaignMutation.Variables
  > =
  ref(
    
      campaignName=campaignName,campaignStartDate=campaignStartDate,campaignEndDate=campaignEndDate,address=address,type=type,
  
    block_
    
  ).execute()


