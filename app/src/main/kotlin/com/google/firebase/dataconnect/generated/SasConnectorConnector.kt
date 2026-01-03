
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

import com.google.firebase.dataconnect.getInstance as _fdcGetInstance

public interface SasConnectorConnector : com.google.firebase.dataconnect.generated.GeneratedConnector<SasConnectorConnector> {
  override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect

  
    public val createAppointment: CreateAppointmentMutation
  
    public val createBeneficiary: CreateBeneficiaryMutation
  
    public val createCategory: CreateCategoryMutation
  
    public val createDistribution: CreateDistributionMutation
  
    public val createDistributionItem: CreateDistributionItemMutation
  
    public val createDonation: CreateDonationMutation
  
    public val createDonationItem: CreateDonationItemMutation
  
    public val createLot: CreateLotMutation
  
    public val createProduct: CreateProductMutation
  
    public val createStatusType: CreateStatusTypeMutation
  
    public val createUser: CreateUserMutation
  
    public val deactivateBeneficiary: DeactivateBeneficiaryMutation
  
    public val deleteAppointment: DeleteAppointmentMutation
  
    public val deleteBeneficiary: DeleteBeneficiaryMutation
  
    public val deleteCategory: DeleteCategoryMutation
  
    public val deleteDistribution: DeleteDistributionMutation
  
    public val deleteDistributionItem: DeleteDistributionItemMutation
  
    public val deleteDonation: DeleteDonationMutation
  
    public val deleteDonationItem: DeleteDonationItemMutation
  
    public val deleteLot: DeleteLotMutation
  
    public val deleteProduct: DeleteProductMutation
  
    public val deleteStatusType: DeleteStatusTypeMutation
  
    public val deleteUser: DeleteUserMutation
  
    public val getAppointmentById: GetAppointmentByIdQuery
  
    public val getBeneficiaryById: GetBeneficiaryByIdQuery
  
    public val getCategoryById: GetCategoryByIdQuery
  
    public val getDistributionById: GetDistributionByIdQuery
  
    public val getDonationById: GetDonationByIdQuery
  
    public val getLotById: GetLotByIdQuery
  
    public val getProductById: GetProductByIdQuery
  
    public val getStatusTypeById: GetStatusTypeByIdQuery
  
    public val getUserById: GetUserByIdQuery
  
    public val listActiveBeneficiaries: ListActiveBeneficiariesQuery
  
    public val listActiveCategories: ListActiveCategoriesQuery
  
    public val listActiveStatusTypes: ListActiveStatusTypesQuery
  
    public val listAppointments: ListAppointmentsQuery
  
    public val listAppointmentsByDateRange: ListAppointmentsByDateRangeQuery
  
    public val listBeneficiaries: ListBeneficiariesQuery
  
    public val listCategories: ListCategoriesQuery
  
    public val listDistributionItemsByDistribution: ListDistributionItemsByDistributionQuery
  
    public val listDistributionItemsByLot: ListDistributionItemsByLotQuery
  
    public val listDistributions: ListDistributionsQuery
  
    public val listDistributionsByBeneficiary: ListDistributionsByBeneficiaryQuery
  
    public val listDistributionsByStatus: ListDistributionsByStatusQuery
  
    public val listDonationItemsByDonation: ListDonationItemsByDonationQuery
  
    public val listDonationItemsByLot: ListDonationItemsByLotQuery
  
    public val listDonations: ListDonationsQuery
  
    public val listLots: ListLotsQuery
  
    public val listLotsByProduct: ListLotsByProductQuery
  
    public val listProducts: ListProductsQuery
  
    public val listProductsByCategory: ListProductsByCategoryQuery
  
    public val listStatusTypes: ListStatusTypesQuery
  
    public val listUsers: ListUsersQuery
  
    public val searchBeneficiaries: SearchBeneficiariesQuery
  
    public val searchDonations: SearchDonationsQuery
  
    public val searchLots: SearchLotsQuery
  
    public val searchProducts: SearchProductsQuery
  
    public val searchUsers: SearchUsersQuery
  
    public val updateAppointment: UpdateAppointmentMutation
  
    public val updateAppointmentStatus: UpdateAppointmentStatusMutation
  
    public val updateBeneficiary: UpdateBeneficiaryMutation
  
    public val updateCategory: UpdateCategoryMutation
  
    public val updateDistribution: UpdateDistributionMutation
  
    public val updateDistributionItem: UpdateDistributionItemMutation
  
    public val updateDistributionStatus: UpdateDistributionStatusMutation
  
    public val updateDonation: UpdateDonationMutation
  
    public val updateDonationItem: UpdateDonationItemMutation
  
    public val updateDonationStatus: UpdateDonationStatusMutation
  
    public val updateLot: UpdateLotMutation
  
    public val updateLotQuantity: UpdateLotQuantityMutation
  
    public val updateProduct: UpdateProductMutation
  
    public val updateStatusType: UpdateStatusTypeMutation
  
    public val updateUser: UpdateUserMutation
  

  public companion object {
    @Suppress("MemberVisibilityCanBePrivate")
    public val config: com.google.firebase.dataconnect.ConnectorConfig = com.google.firebase.dataconnect.ConnectorConfig(
      connector = "sas-connector",
      location = "europe-west1",
      serviceId = "loja-social-ipca-projeto",
    )

    public fun getInstance(
      dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
    ):SasConnectorConnector = synchronized(instances) {
      instances.getOrPut(dataConnect) {
        SasConnectorConnectorImpl(dataConnect)
      }
    }

    private val instances = java.util.WeakHashMap<com.google.firebase.dataconnect.FirebaseDataConnect, SasConnectorConnectorImpl>()
  }
}

public val SasConnectorConnector.Companion.instance:SasConnectorConnector
  get() = getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(config))

public fun SasConnectorConnector.Companion.getInstance(
  settings: com.google.firebase.dataconnect.DataConnectSettings = com.google.firebase.dataconnect.DataConnectSettings()
):SasConnectorConnector =
  getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(config, settings))

public fun SasConnectorConnector.Companion.getInstance(
  app: com.google.firebase.FirebaseApp,
  settings: com.google.firebase.dataconnect.DataConnectSettings = com.google.firebase.dataconnect.DataConnectSettings()
):SasConnectorConnector =
  getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(app, config, settings))

private class SasConnectorConnectorImpl(
  override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
) : SasConnectorConnector {
  
    override val createAppointment by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateAppointmentMutationImpl(this)
    }
  
    override val createBeneficiary by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateBeneficiaryMutationImpl(this)
    }
  
    override val createCategory by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateCategoryMutationImpl(this)
    }
  
    override val createDistribution by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateDistributionMutationImpl(this)
    }
  
    override val createDistributionItem by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateDistributionItemMutationImpl(this)
    }
  
    override val createDonation by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateDonationMutationImpl(this)
    }
  
    override val createDonationItem by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateDonationItemMutationImpl(this)
    }
  
    override val createLot by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateLotMutationImpl(this)
    }
  
    override val createProduct by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateProductMutationImpl(this)
    }
  
    override val createStatusType by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateStatusTypeMutationImpl(this)
    }
  
    override val createUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateUserMutationImpl(this)
    }
  
    override val deactivateBeneficiary by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeactivateBeneficiaryMutationImpl(this)
    }
  
    override val deleteAppointment by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteAppointmentMutationImpl(this)
    }
  
    override val deleteBeneficiary by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteBeneficiaryMutationImpl(this)
    }
  
    override val deleteCategory by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteCategoryMutationImpl(this)
    }
  
    override val deleteDistribution by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteDistributionMutationImpl(this)
    }
  
    override val deleteDistributionItem by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteDistributionItemMutationImpl(this)
    }
  
    override val deleteDonation by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteDonationMutationImpl(this)
    }
  
    override val deleteDonationItem by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteDonationItemMutationImpl(this)
    }
  
    override val deleteLot by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteLotMutationImpl(this)
    }
  
    override val deleteProduct by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteProductMutationImpl(this)
    }
  
    override val deleteStatusType by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteStatusTypeMutationImpl(this)
    }
  
    override val deleteUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteUserMutationImpl(this)
    }
  
    override val getAppointmentById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetAppointmentByIdQueryImpl(this)
    }
  
    override val getBeneficiaryById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetBeneficiaryByIdQueryImpl(this)
    }
  
    override val getCategoryById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetCategoryByIdQueryImpl(this)
    }
  
    override val getDistributionById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetDistributionByIdQueryImpl(this)
    }
  
    override val getDonationById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetDonationByIdQueryImpl(this)
    }
  
    override val getLotById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetLotByIdQueryImpl(this)
    }
  
    override val getProductById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetProductByIdQueryImpl(this)
    }
  
    override val getStatusTypeById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetStatusTypeByIdQueryImpl(this)
    }
  
    override val getUserById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetUserByIdQueryImpl(this)
    }
  
    override val listActiveBeneficiaries by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListActiveBeneficiariesQueryImpl(this)
    }
  
    override val listActiveCategories by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListActiveCategoriesQueryImpl(this)
    }
  
    override val listActiveStatusTypes by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListActiveStatusTypesQueryImpl(this)
    }
  
    override val listAppointments by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListAppointmentsQueryImpl(this)
    }
  
    override val listAppointmentsByDateRange by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListAppointmentsByDateRangeQueryImpl(this)
    }
  
    override val listBeneficiaries by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListBeneficiariesQueryImpl(this)
    }
  
    override val listCategories by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListCategoriesQueryImpl(this)
    }
  
    override val listDistributionItemsByDistribution by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDistributionItemsByDistributionQueryImpl(this)
    }
  
    override val listDistributionItemsByLot by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDistributionItemsByLotQueryImpl(this)
    }
  
    override val listDistributions by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDistributionsQueryImpl(this)
    }
  
    override val listDistributionsByBeneficiary by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDistributionsByBeneficiaryQueryImpl(this)
    }
  
    override val listDistributionsByStatus by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDistributionsByStatusQueryImpl(this)
    }
  
    override val listDonationItemsByDonation by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDonationItemsByDonationQueryImpl(this)
    }
  
    override val listDonationItemsByLot by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDonationItemsByLotQueryImpl(this)
    }
  
    override val listDonations by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListDonationsQueryImpl(this)
    }
  
    override val listLots by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListLotsQueryImpl(this)
    }
  
    override val listLotsByProduct by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListLotsByProductQueryImpl(this)
    }
  
    override val listProducts by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListProductsQueryImpl(this)
    }
  
    override val listProductsByCategory by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListProductsByCategoryQueryImpl(this)
    }
  
    override val listStatusTypes by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListStatusTypesQueryImpl(this)
    }
  
    override val listUsers by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListUsersQueryImpl(this)
    }
  
    override val searchBeneficiaries by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SearchBeneficiariesQueryImpl(this)
    }
  
    override val searchDonations by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SearchDonationsQueryImpl(this)
    }
  
    override val searchLots by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SearchLotsQueryImpl(this)
    }
  
    override val searchProducts by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SearchProductsQueryImpl(this)
    }
  
    override val searchUsers by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SearchUsersQueryImpl(this)
    }
  
    override val updateAppointment by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateAppointmentMutationImpl(this)
    }
  
    override val updateAppointmentStatus by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateAppointmentStatusMutationImpl(this)
    }
  
    override val updateBeneficiary by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateBeneficiaryMutationImpl(this)
    }
  
    override val updateCategory by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateCategoryMutationImpl(this)
    }
  
    override val updateDistribution by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateDistributionMutationImpl(this)
    }
  
    override val updateDistributionItem by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateDistributionItemMutationImpl(this)
    }
  
    override val updateDistributionStatus by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateDistributionStatusMutationImpl(this)
    }
  
    override val updateDonation by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateDonationMutationImpl(this)
    }
  
    override val updateDonationItem by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateDonationItemMutationImpl(this)
    }
  
    override val updateDonationStatus by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateDonationStatusMutationImpl(this)
    }
  
    override val updateLot by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateLotMutationImpl(this)
    }
  
    override val updateLotQuantity by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateLotQuantityMutationImpl(this)
    }
  
    override val updateProduct by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateProductMutationImpl(this)
    }
  
    override val updateStatusType by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateStatusTypeMutationImpl(this)
    }
  
    override val updateUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateUserMutationImpl(this)
    }
  

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun operations(): List<com.google.firebase.dataconnect.generated.GeneratedOperation<SasConnectorConnector, *, *>> =
    queries() + mutations()

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun mutations(): List<com.google.firebase.dataconnect.generated.GeneratedMutation<SasConnectorConnector, *, *>> =
    listOf(
      createAppointment,
        createBeneficiary,
        createCategory,
        createDistribution,
        createDistributionItem,
        createDonation,
        createDonationItem,
        createLot,
        createProduct,
        createStatusType,
        createUser,
        deactivateBeneficiary,
        deleteAppointment,
        deleteBeneficiary,
        deleteCategory,
        deleteDistribution,
        deleteDistributionItem,
        deleteDonation,
        deleteDonationItem,
        deleteLot,
        deleteProduct,
        deleteStatusType,
        deleteUser,
        updateAppointment,
        updateAppointmentStatus,
        updateBeneficiary,
        updateCategory,
        updateDistribution,
        updateDistributionItem,
        updateDistributionStatus,
        updateDonation,
        updateDonationItem,
        updateDonationStatus,
        updateLot,
        updateLotQuantity,
        updateProduct,
        updateStatusType,
        updateUser,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun queries(): List<com.google.firebase.dataconnect.generated.GeneratedQuery<SasConnectorConnector, *, *>> =
    listOf(
      getAppointmentById,
        getBeneficiaryById,
        getCategoryById,
        getDistributionById,
        getDonationById,
        getLotById,
        getProductById,
        getStatusTypeById,
        getUserById,
        listActiveBeneficiaries,
        listActiveCategories,
        listActiveStatusTypes,
        listAppointments,
        listAppointmentsByDateRange,
        listBeneficiaries,
        listCategories,
        listDistributionItemsByDistribution,
        listDistributionItemsByLot,
        listDistributions,
        listDistributionsByBeneficiary,
        listDistributionsByStatus,
        listDonationItemsByDonation,
        listDonationItemsByLot,
        listDonations,
        listLots,
        listLotsByProduct,
        listProducts,
        listProductsByCategory,
        listStatusTypes,
        listUsers,
        searchBeneficiaries,
        searchDonations,
        searchLots,
        searchProducts,
        searchUsers,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect) =
    SasConnectorConnectorImpl(dataConnect)

  override fun equals(other: Any?): Boolean =
    other is SasConnectorConnectorImpl &&
    other.dataConnect == dataConnect

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "SasConnectorConnectorImpl",
      dataConnect,
    )

  override fun toString(): String =
    "SasConnectorConnectorImpl(dataConnect=$dataConnect)"
}



private open class SasConnectorConnectorGeneratedQueryImpl<Data, Variables>(
  override val connector: SasConnectorConnector,
  override val operationName: String,
  override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
  override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedQuery<SasConnectorConnector, Data, Variables> {

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(
    connector: SasConnectorConnector,
    operationName: String,
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
  ) =
    SasConnectorConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewVariables> withVariablesSerializer(
    variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
  ) =
    SasConnectorConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewData> withDataDeserializer(
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
  ) =
    SasConnectorConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun equals(other: Any?): Boolean =
    other is SasConnectorConnectorGeneratedQueryImpl<*,*> &&
    other.connector == connector &&
    other.operationName == operationName &&
    other.dataDeserializer == dataDeserializer &&
    other.variablesSerializer == variablesSerializer

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "SasConnectorConnectorGeneratedQueryImpl",
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun toString(): String =
    "SasConnectorConnectorGeneratedQueryImpl(" +
    "operationName=$operationName, " +
    "dataDeserializer=$dataDeserializer, " +
    "variablesSerializer=$variablesSerializer, " +
    "connector=$connector)"
}

private open class SasConnectorConnectorGeneratedMutationImpl<Data, Variables>(
  override val connector: SasConnectorConnector,
  override val operationName: String,
  override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
  override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedMutation<SasConnectorConnector, Data, Variables> {

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(
    connector: SasConnectorConnector,
    operationName: String,
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
  ) =
    SasConnectorConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewVariables> withVariablesSerializer(
    variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
  ) =
    SasConnectorConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewData> withDataDeserializer(
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
  ) =
    SasConnectorConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun equals(other: Any?): Boolean =
    other is SasConnectorConnectorGeneratedMutationImpl<*,*> &&
    other.connector == connector &&
    other.operationName == operationName &&
    other.dataDeserializer == dataDeserializer &&
    other.variablesSerializer == variablesSerializer

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "SasConnectorConnectorGeneratedMutationImpl",
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun toString(): String =
    "SasConnectorConnectorGeneratedMutationImpl(" +
    "operationName=$operationName, " +
    "dataDeserializer=$dataDeserializer, " +
    "variablesSerializer=$variablesSerializer, " +
    "connector=$connector)"
}



private class CreateAppointmentMutationImpl(
  connector: SasConnectorConnector
):
  CreateAppointmentMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateAppointmentMutation.Data,
      CreateAppointmentMutation.Variables
  >(
    connector,
    CreateAppointmentMutation.Companion.operationName,
    CreateAppointmentMutation.Companion.dataDeserializer,
    CreateAppointmentMutation.Companion.variablesSerializer,
  )


private class CreateBeneficiaryMutationImpl(
  connector: SasConnectorConnector
):
  CreateBeneficiaryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateBeneficiaryMutation.Data,
      CreateBeneficiaryMutation.Variables
  >(
    connector,
    CreateBeneficiaryMutation.Companion.operationName,
    CreateBeneficiaryMutation.Companion.dataDeserializer,
    CreateBeneficiaryMutation.Companion.variablesSerializer,
  )


private class CreateCategoryMutationImpl(
  connector: SasConnectorConnector
):
  CreateCategoryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateCategoryMutation.Data,
      CreateCategoryMutation.Variables
  >(
    connector,
    CreateCategoryMutation.Companion.operationName,
    CreateCategoryMutation.Companion.dataDeserializer,
    CreateCategoryMutation.Companion.variablesSerializer,
  )


private class CreateDistributionMutationImpl(
  connector: SasConnectorConnector
):
  CreateDistributionMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateDistributionMutation.Data,
      CreateDistributionMutation.Variables
  >(
    connector,
    CreateDistributionMutation.Companion.operationName,
    CreateDistributionMutation.Companion.dataDeserializer,
    CreateDistributionMutation.Companion.variablesSerializer,
  )


private class CreateDistributionItemMutationImpl(
  connector: SasConnectorConnector
):
  CreateDistributionItemMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateDistributionItemMutation.Data,
      CreateDistributionItemMutation.Variables
  >(
    connector,
    CreateDistributionItemMutation.Companion.operationName,
    CreateDistributionItemMutation.Companion.dataDeserializer,
    CreateDistributionItemMutation.Companion.variablesSerializer,
  )


private class CreateDonationMutationImpl(
  connector: SasConnectorConnector
):
  CreateDonationMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateDonationMutation.Data,
      CreateDonationMutation.Variables
  >(
    connector,
    CreateDonationMutation.Companion.operationName,
    CreateDonationMutation.Companion.dataDeserializer,
    CreateDonationMutation.Companion.variablesSerializer,
  )


private class CreateDonationItemMutationImpl(
  connector: SasConnectorConnector
):
  CreateDonationItemMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateDonationItemMutation.Data,
      CreateDonationItemMutation.Variables
  >(
    connector,
    CreateDonationItemMutation.Companion.operationName,
    CreateDonationItemMutation.Companion.dataDeserializer,
    CreateDonationItemMutation.Companion.variablesSerializer,
  )


private class CreateLotMutationImpl(
  connector: SasConnectorConnector
):
  CreateLotMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateLotMutation.Data,
      CreateLotMutation.Variables
  >(
    connector,
    CreateLotMutation.Companion.operationName,
    CreateLotMutation.Companion.dataDeserializer,
    CreateLotMutation.Companion.variablesSerializer,
  )


private class CreateProductMutationImpl(
  connector: SasConnectorConnector
):
  CreateProductMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateProductMutation.Data,
      CreateProductMutation.Variables
  >(
    connector,
    CreateProductMutation.Companion.operationName,
    CreateProductMutation.Companion.dataDeserializer,
    CreateProductMutation.Companion.variablesSerializer,
  )


private class CreateStatusTypeMutationImpl(
  connector: SasConnectorConnector
):
  CreateStatusTypeMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateStatusTypeMutation.Data,
      CreateStatusTypeMutation.Variables
  >(
    connector,
    CreateStatusTypeMutation.Companion.operationName,
    CreateStatusTypeMutation.Companion.dataDeserializer,
    CreateStatusTypeMutation.Companion.variablesSerializer,
  )


private class CreateUserMutationImpl(
  connector: SasConnectorConnector
):
  CreateUserMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      CreateUserMutation.Data,
      CreateUserMutation.Variables
  >(
    connector,
    CreateUserMutation.Companion.operationName,
    CreateUserMutation.Companion.dataDeserializer,
    CreateUserMutation.Companion.variablesSerializer,
  )


private class DeactivateBeneficiaryMutationImpl(
  connector: SasConnectorConnector
):
  DeactivateBeneficiaryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeactivateBeneficiaryMutation.Data,
      DeactivateBeneficiaryMutation.Variables
  >(
    connector,
    DeactivateBeneficiaryMutation.Companion.operationName,
    DeactivateBeneficiaryMutation.Companion.dataDeserializer,
    DeactivateBeneficiaryMutation.Companion.variablesSerializer,
  )


private class DeleteAppointmentMutationImpl(
  connector: SasConnectorConnector
):
  DeleteAppointmentMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteAppointmentMutation.Data,
      DeleteAppointmentMutation.Variables
  >(
    connector,
    DeleteAppointmentMutation.Companion.operationName,
    DeleteAppointmentMutation.Companion.dataDeserializer,
    DeleteAppointmentMutation.Companion.variablesSerializer,
  )


private class DeleteBeneficiaryMutationImpl(
  connector: SasConnectorConnector
):
  DeleteBeneficiaryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteBeneficiaryMutation.Data,
      DeleteBeneficiaryMutation.Variables
  >(
    connector,
    DeleteBeneficiaryMutation.Companion.operationName,
    DeleteBeneficiaryMutation.Companion.dataDeserializer,
    DeleteBeneficiaryMutation.Companion.variablesSerializer,
  )


private class DeleteCategoryMutationImpl(
  connector: SasConnectorConnector
):
  DeleteCategoryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteCategoryMutation.Data,
      DeleteCategoryMutation.Variables
  >(
    connector,
    DeleteCategoryMutation.Companion.operationName,
    DeleteCategoryMutation.Companion.dataDeserializer,
    DeleteCategoryMutation.Companion.variablesSerializer,
  )


private class DeleteDistributionMutationImpl(
  connector: SasConnectorConnector
):
  DeleteDistributionMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteDistributionMutation.Data,
      DeleteDistributionMutation.Variables
  >(
    connector,
    DeleteDistributionMutation.Companion.operationName,
    DeleteDistributionMutation.Companion.dataDeserializer,
    DeleteDistributionMutation.Companion.variablesSerializer,
  )


private class DeleteDistributionItemMutationImpl(
  connector: SasConnectorConnector
):
  DeleteDistributionItemMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteDistributionItemMutation.Data,
      DeleteDistributionItemMutation.Variables
  >(
    connector,
    DeleteDistributionItemMutation.Companion.operationName,
    DeleteDistributionItemMutation.Companion.dataDeserializer,
    DeleteDistributionItemMutation.Companion.variablesSerializer,
  )


private class DeleteDonationMutationImpl(
  connector: SasConnectorConnector
):
  DeleteDonationMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteDonationMutation.Data,
      DeleteDonationMutation.Variables
  >(
    connector,
    DeleteDonationMutation.Companion.operationName,
    DeleteDonationMutation.Companion.dataDeserializer,
    DeleteDonationMutation.Companion.variablesSerializer,
  )


private class DeleteDonationItemMutationImpl(
  connector: SasConnectorConnector
):
  DeleteDonationItemMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteDonationItemMutation.Data,
      DeleteDonationItemMutation.Variables
  >(
    connector,
    DeleteDonationItemMutation.Companion.operationName,
    DeleteDonationItemMutation.Companion.dataDeserializer,
    DeleteDonationItemMutation.Companion.variablesSerializer,
  )


private class DeleteLotMutationImpl(
  connector: SasConnectorConnector
):
  DeleteLotMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteLotMutation.Data,
      DeleteLotMutation.Variables
  >(
    connector,
    DeleteLotMutation.Companion.operationName,
    DeleteLotMutation.Companion.dataDeserializer,
    DeleteLotMutation.Companion.variablesSerializer,
  )


private class DeleteProductMutationImpl(
  connector: SasConnectorConnector
):
  DeleteProductMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteProductMutation.Data,
      DeleteProductMutation.Variables
  >(
    connector,
    DeleteProductMutation.Companion.operationName,
    DeleteProductMutation.Companion.dataDeserializer,
    DeleteProductMutation.Companion.variablesSerializer,
  )


private class DeleteStatusTypeMutationImpl(
  connector: SasConnectorConnector
):
  DeleteStatusTypeMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteStatusTypeMutation.Data,
      DeleteStatusTypeMutation.Variables
  >(
    connector,
    DeleteStatusTypeMutation.Companion.operationName,
    DeleteStatusTypeMutation.Companion.dataDeserializer,
    DeleteStatusTypeMutation.Companion.variablesSerializer,
  )


private class DeleteUserMutationImpl(
  connector: SasConnectorConnector
):
  DeleteUserMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      DeleteUserMutation.Data,
      DeleteUserMutation.Variables
  >(
    connector,
    DeleteUserMutation.Companion.operationName,
    DeleteUserMutation.Companion.dataDeserializer,
    DeleteUserMutation.Companion.variablesSerializer,
  )


private class GetAppointmentByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetAppointmentByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetAppointmentByIdQuery.Data,
      GetAppointmentByIdQuery.Variables
  >(
    connector,
    GetAppointmentByIdQuery.Companion.operationName,
    GetAppointmentByIdQuery.Companion.dataDeserializer,
    GetAppointmentByIdQuery.Companion.variablesSerializer,
  )


private class GetBeneficiaryByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetBeneficiaryByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetBeneficiaryByIdQuery.Data,
      GetBeneficiaryByIdQuery.Variables
  >(
    connector,
    GetBeneficiaryByIdQuery.Companion.operationName,
    GetBeneficiaryByIdQuery.Companion.dataDeserializer,
    GetBeneficiaryByIdQuery.Companion.variablesSerializer,
  )


private class GetCategoryByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetCategoryByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetCategoryByIdQuery.Data,
      GetCategoryByIdQuery.Variables
  >(
    connector,
    GetCategoryByIdQuery.Companion.operationName,
    GetCategoryByIdQuery.Companion.dataDeserializer,
    GetCategoryByIdQuery.Companion.variablesSerializer,
  )


private class GetDistributionByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetDistributionByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetDistributionByIdQuery.Data,
      GetDistributionByIdQuery.Variables
  >(
    connector,
    GetDistributionByIdQuery.Companion.operationName,
    GetDistributionByIdQuery.Companion.dataDeserializer,
    GetDistributionByIdQuery.Companion.variablesSerializer,
  )


private class GetDonationByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetDonationByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetDonationByIdQuery.Data,
      GetDonationByIdQuery.Variables
  >(
    connector,
    GetDonationByIdQuery.Companion.operationName,
    GetDonationByIdQuery.Companion.dataDeserializer,
    GetDonationByIdQuery.Companion.variablesSerializer,
  )


private class GetLotByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetLotByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetLotByIdQuery.Data,
      GetLotByIdQuery.Variables
  >(
    connector,
    GetLotByIdQuery.Companion.operationName,
    GetLotByIdQuery.Companion.dataDeserializer,
    GetLotByIdQuery.Companion.variablesSerializer,
  )


private class GetProductByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetProductByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetProductByIdQuery.Data,
      GetProductByIdQuery.Variables
  >(
    connector,
    GetProductByIdQuery.Companion.operationName,
    GetProductByIdQuery.Companion.dataDeserializer,
    GetProductByIdQuery.Companion.variablesSerializer,
  )


private class GetStatusTypeByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetStatusTypeByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetStatusTypeByIdQuery.Data,
      GetStatusTypeByIdQuery.Variables
  >(
    connector,
    GetStatusTypeByIdQuery.Companion.operationName,
    GetStatusTypeByIdQuery.Companion.dataDeserializer,
    GetStatusTypeByIdQuery.Companion.variablesSerializer,
  )


private class GetUserByIdQueryImpl(
  connector: SasConnectorConnector
):
  GetUserByIdQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      GetUserByIdQuery.Data,
      GetUserByIdQuery.Variables
  >(
    connector,
    GetUserByIdQuery.Companion.operationName,
    GetUserByIdQuery.Companion.dataDeserializer,
    GetUserByIdQuery.Companion.variablesSerializer,
  )


private class ListActiveBeneficiariesQueryImpl(
  connector: SasConnectorConnector
):
  ListActiveBeneficiariesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListActiveBeneficiariesQuery.Data,
      ListActiveBeneficiariesQuery.Variables
  >(
    connector,
    ListActiveBeneficiariesQuery.Companion.operationName,
    ListActiveBeneficiariesQuery.Companion.dataDeserializer,
    ListActiveBeneficiariesQuery.Companion.variablesSerializer,
  )


private class ListActiveCategoriesQueryImpl(
  connector: SasConnectorConnector
):
  ListActiveCategoriesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListActiveCategoriesQuery.Data,
      ListActiveCategoriesQuery.Variables
  >(
    connector,
    ListActiveCategoriesQuery.Companion.operationName,
    ListActiveCategoriesQuery.Companion.dataDeserializer,
    ListActiveCategoriesQuery.Companion.variablesSerializer,
  )


private class ListActiveStatusTypesQueryImpl(
  connector: SasConnectorConnector
):
  ListActiveStatusTypesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListActiveStatusTypesQuery.Data,
      ListActiveStatusTypesQuery.Variables
  >(
    connector,
    ListActiveStatusTypesQuery.Companion.operationName,
    ListActiveStatusTypesQuery.Companion.dataDeserializer,
    ListActiveStatusTypesQuery.Companion.variablesSerializer,
  )


private class ListAppointmentsQueryImpl(
  connector: SasConnectorConnector
):
  ListAppointmentsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListAppointmentsQuery.Data,
      ListAppointmentsQuery.Variables
  >(
    connector,
    ListAppointmentsQuery.Companion.operationName,
    ListAppointmentsQuery.Companion.dataDeserializer,
    ListAppointmentsQuery.Companion.variablesSerializer,
  )


private class ListAppointmentsByDateRangeQueryImpl(
  connector: SasConnectorConnector
):
  ListAppointmentsByDateRangeQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListAppointmentsByDateRangeQuery.Data,
      ListAppointmentsByDateRangeQuery.Variables
  >(
    connector,
    ListAppointmentsByDateRangeQuery.Companion.operationName,
    ListAppointmentsByDateRangeQuery.Companion.dataDeserializer,
    ListAppointmentsByDateRangeQuery.Companion.variablesSerializer,
  )


private class ListBeneficiariesQueryImpl(
  connector: SasConnectorConnector
):
  ListBeneficiariesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListBeneficiariesQuery.Data,
      ListBeneficiariesQuery.Variables
  >(
    connector,
    ListBeneficiariesQuery.Companion.operationName,
    ListBeneficiariesQuery.Companion.dataDeserializer,
    ListBeneficiariesQuery.Companion.variablesSerializer,
  )


private class ListCategoriesQueryImpl(
  connector: SasConnectorConnector
):
  ListCategoriesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListCategoriesQuery.Data,
      ListCategoriesQuery.Variables
  >(
    connector,
    ListCategoriesQuery.Companion.operationName,
    ListCategoriesQuery.Companion.dataDeserializer,
    ListCategoriesQuery.Companion.variablesSerializer,
  )


private class ListDistributionItemsByDistributionQueryImpl(
  connector: SasConnectorConnector
):
  ListDistributionItemsByDistributionQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDistributionItemsByDistributionQuery.Data,
      ListDistributionItemsByDistributionQuery.Variables
  >(
    connector,
    ListDistributionItemsByDistributionQuery.Companion.operationName,
    ListDistributionItemsByDistributionQuery.Companion.dataDeserializer,
    ListDistributionItemsByDistributionQuery.Companion.variablesSerializer,
  )


private class ListDistributionItemsByLotQueryImpl(
  connector: SasConnectorConnector
):
  ListDistributionItemsByLotQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDistributionItemsByLotQuery.Data,
      ListDistributionItemsByLotQuery.Variables
  >(
    connector,
    ListDistributionItemsByLotQuery.Companion.operationName,
    ListDistributionItemsByLotQuery.Companion.dataDeserializer,
    ListDistributionItemsByLotQuery.Companion.variablesSerializer,
  )


private class ListDistributionsQueryImpl(
  connector: SasConnectorConnector
):
  ListDistributionsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDistributionsQuery.Data,
      ListDistributionsQuery.Variables
  >(
    connector,
    ListDistributionsQuery.Companion.operationName,
    ListDistributionsQuery.Companion.dataDeserializer,
    ListDistributionsQuery.Companion.variablesSerializer,
  )


private class ListDistributionsByBeneficiaryQueryImpl(
  connector: SasConnectorConnector
):
  ListDistributionsByBeneficiaryQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDistributionsByBeneficiaryQuery.Data,
      ListDistributionsByBeneficiaryQuery.Variables
  >(
    connector,
    ListDistributionsByBeneficiaryQuery.Companion.operationName,
    ListDistributionsByBeneficiaryQuery.Companion.dataDeserializer,
    ListDistributionsByBeneficiaryQuery.Companion.variablesSerializer,
  )


private class ListDistributionsByStatusQueryImpl(
  connector: SasConnectorConnector
):
  ListDistributionsByStatusQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDistributionsByStatusQuery.Data,
      ListDistributionsByStatusQuery.Variables
  >(
    connector,
    ListDistributionsByStatusQuery.Companion.operationName,
    ListDistributionsByStatusQuery.Companion.dataDeserializer,
    ListDistributionsByStatusQuery.Companion.variablesSerializer,
  )


private class ListDonationItemsByDonationQueryImpl(
  connector: SasConnectorConnector
):
  ListDonationItemsByDonationQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDonationItemsByDonationQuery.Data,
      ListDonationItemsByDonationQuery.Variables
  >(
    connector,
    ListDonationItemsByDonationQuery.Companion.operationName,
    ListDonationItemsByDonationQuery.Companion.dataDeserializer,
    ListDonationItemsByDonationQuery.Companion.variablesSerializer,
  )


private class ListDonationItemsByLotQueryImpl(
  connector: SasConnectorConnector
):
  ListDonationItemsByLotQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDonationItemsByLotQuery.Data,
      ListDonationItemsByLotQuery.Variables
  >(
    connector,
    ListDonationItemsByLotQuery.Companion.operationName,
    ListDonationItemsByLotQuery.Companion.dataDeserializer,
    ListDonationItemsByLotQuery.Companion.variablesSerializer,
  )


private class ListDonationsQueryImpl(
  connector: SasConnectorConnector
):
  ListDonationsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListDonationsQuery.Data,
      ListDonationsQuery.Variables
  >(
    connector,
    ListDonationsQuery.Companion.operationName,
    ListDonationsQuery.Companion.dataDeserializer,
    ListDonationsQuery.Companion.variablesSerializer,
  )


private class ListLotsQueryImpl(
  connector: SasConnectorConnector
):
  ListLotsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListLotsQuery.Data,
      ListLotsQuery.Variables
  >(
    connector,
    ListLotsQuery.Companion.operationName,
    ListLotsQuery.Companion.dataDeserializer,
    ListLotsQuery.Companion.variablesSerializer,
  )


private class ListLotsByProductQueryImpl(
  connector: SasConnectorConnector
):
  ListLotsByProductQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListLotsByProductQuery.Data,
      ListLotsByProductQuery.Variables
  >(
    connector,
    ListLotsByProductQuery.Companion.operationName,
    ListLotsByProductQuery.Companion.dataDeserializer,
    ListLotsByProductQuery.Companion.variablesSerializer,
  )


private class ListProductsQueryImpl(
  connector: SasConnectorConnector
):
  ListProductsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListProductsQuery.Data,
      ListProductsQuery.Variables
  >(
    connector,
    ListProductsQuery.Companion.operationName,
    ListProductsQuery.Companion.dataDeserializer,
    ListProductsQuery.Companion.variablesSerializer,
  )


private class ListProductsByCategoryQueryImpl(
  connector: SasConnectorConnector
):
  ListProductsByCategoryQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListProductsByCategoryQuery.Data,
      ListProductsByCategoryQuery.Variables
  >(
    connector,
    ListProductsByCategoryQuery.Companion.operationName,
    ListProductsByCategoryQuery.Companion.dataDeserializer,
    ListProductsByCategoryQuery.Companion.variablesSerializer,
  )


private class ListStatusTypesQueryImpl(
  connector: SasConnectorConnector
):
  ListStatusTypesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListStatusTypesQuery.Data,
      ListStatusTypesQuery.Variables
  >(
    connector,
    ListStatusTypesQuery.Companion.operationName,
    ListStatusTypesQuery.Companion.dataDeserializer,
    ListStatusTypesQuery.Companion.variablesSerializer,
  )


private class ListUsersQueryImpl(
  connector: SasConnectorConnector
):
  ListUsersQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      ListUsersQuery.Data,
      ListUsersQuery.Variables
  >(
    connector,
    ListUsersQuery.Companion.operationName,
    ListUsersQuery.Companion.dataDeserializer,
    ListUsersQuery.Companion.variablesSerializer,
  )


private class SearchBeneficiariesQueryImpl(
  connector: SasConnectorConnector
):
  SearchBeneficiariesQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      SearchBeneficiariesQuery.Data,
      SearchBeneficiariesQuery.Variables
  >(
    connector,
    SearchBeneficiariesQuery.Companion.operationName,
    SearchBeneficiariesQuery.Companion.dataDeserializer,
    SearchBeneficiariesQuery.Companion.variablesSerializer,
  )


private class SearchDonationsQueryImpl(
  connector: SasConnectorConnector
):
  SearchDonationsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      SearchDonationsQuery.Data,
      SearchDonationsQuery.Variables
  >(
    connector,
    SearchDonationsQuery.Companion.operationName,
    SearchDonationsQuery.Companion.dataDeserializer,
    SearchDonationsQuery.Companion.variablesSerializer,
  )


private class SearchLotsQueryImpl(
  connector: SasConnectorConnector
):
  SearchLotsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      SearchLotsQuery.Data,
      SearchLotsQuery.Variables
  >(
    connector,
    SearchLotsQuery.Companion.operationName,
    SearchLotsQuery.Companion.dataDeserializer,
    SearchLotsQuery.Companion.variablesSerializer,
  )


private class SearchProductsQueryImpl(
  connector: SasConnectorConnector
):
  SearchProductsQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      SearchProductsQuery.Data,
      SearchProductsQuery.Variables
  >(
    connector,
    SearchProductsQuery.Companion.operationName,
    SearchProductsQuery.Companion.dataDeserializer,
    SearchProductsQuery.Companion.variablesSerializer,
  )


private class SearchUsersQueryImpl(
  connector: SasConnectorConnector
):
  SearchUsersQuery,
  SasConnectorConnectorGeneratedQueryImpl<
      SearchUsersQuery.Data,
      SearchUsersQuery.Variables
  >(
    connector,
    SearchUsersQuery.Companion.operationName,
    SearchUsersQuery.Companion.dataDeserializer,
    SearchUsersQuery.Companion.variablesSerializer,
  )


private class UpdateAppointmentMutationImpl(
  connector: SasConnectorConnector
):
  UpdateAppointmentMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateAppointmentMutation.Data,
      UpdateAppointmentMutation.Variables
  >(
    connector,
    UpdateAppointmentMutation.Companion.operationName,
    UpdateAppointmentMutation.Companion.dataDeserializer,
    UpdateAppointmentMutation.Companion.variablesSerializer,
  )


private class UpdateAppointmentStatusMutationImpl(
  connector: SasConnectorConnector
):
  UpdateAppointmentStatusMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateAppointmentStatusMutation.Data,
      UpdateAppointmentStatusMutation.Variables
  >(
    connector,
    UpdateAppointmentStatusMutation.Companion.operationName,
    UpdateAppointmentStatusMutation.Companion.dataDeserializer,
    UpdateAppointmentStatusMutation.Companion.variablesSerializer,
  )


private class UpdateBeneficiaryMutationImpl(
  connector: SasConnectorConnector
):
  UpdateBeneficiaryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateBeneficiaryMutation.Data,
      UpdateBeneficiaryMutation.Variables
  >(
    connector,
    UpdateBeneficiaryMutation.Companion.operationName,
    UpdateBeneficiaryMutation.Companion.dataDeserializer,
    UpdateBeneficiaryMutation.Companion.variablesSerializer,
  )


private class UpdateCategoryMutationImpl(
  connector: SasConnectorConnector
):
  UpdateCategoryMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateCategoryMutation.Data,
      UpdateCategoryMutation.Variables
  >(
    connector,
    UpdateCategoryMutation.Companion.operationName,
    UpdateCategoryMutation.Companion.dataDeserializer,
    UpdateCategoryMutation.Companion.variablesSerializer,
  )


private class UpdateDistributionMutationImpl(
  connector: SasConnectorConnector
):
  UpdateDistributionMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateDistributionMutation.Data,
      UpdateDistributionMutation.Variables
  >(
    connector,
    UpdateDistributionMutation.Companion.operationName,
    UpdateDistributionMutation.Companion.dataDeserializer,
    UpdateDistributionMutation.Companion.variablesSerializer,
  )


private class UpdateDistributionItemMutationImpl(
  connector: SasConnectorConnector
):
  UpdateDistributionItemMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateDistributionItemMutation.Data,
      UpdateDistributionItemMutation.Variables
  >(
    connector,
    UpdateDistributionItemMutation.Companion.operationName,
    UpdateDistributionItemMutation.Companion.dataDeserializer,
    UpdateDistributionItemMutation.Companion.variablesSerializer,
  )


private class UpdateDistributionStatusMutationImpl(
  connector: SasConnectorConnector
):
  UpdateDistributionStatusMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateDistributionStatusMutation.Data,
      UpdateDistributionStatusMutation.Variables
  >(
    connector,
    UpdateDistributionStatusMutation.Companion.operationName,
    UpdateDistributionStatusMutation.Companion.dataDeserializer,
    UpdateDistributionStatusMutation.Companion.variablesSerializer,
  )


private class UpdateDonationMutationImpl(
  connector: SasConnectorConnector
):
  UpdateDonationMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateDonationMutation.Data,
      UpdateDonationMutation.Variables
  >(
    connector,
    UpdateDonationMutation.Companion.operationName,
    UpdateDonationMutation.Companion.dataDeserializer,
    UpdateDonationMutation.Companion.variablesSerializer,
  )


private class UpdateDonationItemMutationImpl(
  connector: SasConnectorConnector
):
  UpdateDonationItemMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateDonationItemMutation.Data,
      UpdateDonationItemMutation.Variables
  >(
    connector,
    UpdateDonationItemMutation.Companion.operationName,
    UpdateDonationItemMutation.Companion.dataDeserializer,
    UpdateDonationItemMutation.Companion.variablesSerializer,
  )


private class UpdateDonationStatusMutationImpl(
  connector: SasConnectorConnector
):
  UpdateDonationStatusMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateDonationStatusMutation.Data,
      UpdateDonationStatusMutation.Variables
  >(
    connector,
    UpdateDonationStatusMutation.Companion.operationName,
    UpdateDonationStatusMutation.Companion.dataDeserializer,
    UpdateDonationStatusMutation.Companion.variablesSerializer,
  )


private class UpdateLotMutationImpl(
  connector: SasConnectorConnector
):
  UpdateLotMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateLotMutation.Data,
      UpdateLotMutation.Variables
  >(
    connector,
    UpdateLotMutation.Companion.operationName,
    UpdateLotMutation.Companion.dataDeserializer,
    UpdateLotMutation.Companion.variablesSerializer,
  )


private class UpdateLotQuantityMutationImpl(
  connector: SasConnectorConnector
):
  UpdateLotQuantityMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateLotQuantityMutation.Data,
      UpdateLotQuantityMutation.Variables
  >(
    connector,
    UpdateLotQuantityMutation.Companion.operationName,
    UpdateLotQuantityMutation.Companion.dataDeserializer,
    UpdateLotQuantityMutation.Companion.variablesSerializer,
  )


private class UpdateProductMutationImpl(
  connector: SasConnectorConnector
):
  UpdateProductMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateProductMutation.Data,
      UpdateProductMutation.Variables
  >(
    connector,
    UpdateProductMutation.Companion.operationName,
    UpdateProductMutation.Companion.dataDeserializer,
    UpdateProductMutation.Companion.variablesSerializer,
  )


private class UpdateStatusTypeMutationImpl(
  connector: SasConnectorConnector
):
  UpdateStatusTypeMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateStatusTypeMutation.Data,
      UpdateStatusTypeMutation.Variables
  >(
    connector,
    UpdateStatusTypeMutation.Companion.operationName,
    UpdateStatusTypeMutation.Companion.dataDeserializer,
    UpdateStatusTypeMutation.Companion.variablesSerializer,
  )


private class UpdateUserMutationImpl(
  connector: SasConnectorConnector
):
  UpdateUserMutation,
  SasConnectorConnectorGeneratedMutationImpl<
      UpdateUserMutation.Data,
      UpdateUserMutation.Variables
  >(
    connector,
    UpdateUserMutation.Companion.operationName,
    UpdateUserMutation.Companion.dataDeserializer,
    UpdateUserMutation.Companion.variablesSerializer,
  )


