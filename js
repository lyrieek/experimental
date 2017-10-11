'use strict';

angular.module('angulardemoApp')
  .controller('UpdateI18nDataCtrl',function ($rootScope,$scope,$timeout,$window, $http, $translate,$state,$stateParams,prompt2,parseException,parseDelException) {


    $timeout(function(){
      $translate(['APP2001','APP2002','Key','Label','LabelL2','LabelL3','Module','Line','UpdateI18nDataSuccess','UpdateI18nDataError'])
      .then(function (translations) {
        $scope.APP2001=translations.APP2001;
        $scope.APP2002=translations.APP2002;
        $scope.Key=translations.Key;
        $scope.Label=translations.Label;
        $scope.LabelL2=translations.LabelL2;
        $scope.LabelL3=translations.LabelL3;
        $scope.Module=translations.Module;
        $scope.Line=translations.Line;
        $scope.UpdateI18nDataSuccess=translations.UpdateI18nDataSuccess;
        $scope.UpdateI18nDataError=translations.UpdateI18nDataError;

        

        var landata= [{ text: "繁體中文", value: "zh-HK" },{ text: "English", value: "en-US" },{ text: "简体中文", value: "zh-CN" }];
        
        $scope.dataSource_error=function(e) {
          parseException.alertmsg(e,$rootScope.errorMessages,$scope.ExceptionDialog,$scope.APP2002,$scope.confirmLabel);
        }


        $scope.updateI18nModuleDS = new kendo.data.DataSource({
            transport: {
              read: {
                url: $scope.serviceRoot+"/i18nData/queryAllModule",
                dataType: "json",
                type: "POST",
                contentType: "application/json"
              }
            }
        });

        $scope.updateI18nModuleOptions = {
          dataSource: $scope.updateI18nModuleDS,
          change: function(e) {
            $("#moduleValue").val(this.value());
          }
        };

        $scope.updateI18nDataDS = new kendo.data.DataSource({
            batch: true,
            transport: {
              read: {
                  url: $scope.serviceRoot+"/i18nData/readAll",
                  dataType: "json",
                  type: "POST",
                  contentType: "application/json"
              },
              create: {
                  url: $scope.serviceRoot+"/i18nData/create",
                  dataType: "json",
                  type: "POST",
                  contentType: "application/json"
              },
              update: {
                  url: $scope.serviceRoot+"/i18nData/update",
                  dataType: "json",
                  type: "POST",
                  contentType: "application/json"
              },
              parameterMap:function(options, operation) {
                  if (operation !== "read" && options.models) {
                    return JSON.stringify(options.models[0]);
                  }
              }
            },
            sync: function() {
                $scope.updateI18nDataDS.read();
            },
            pageSize:10,
            
            schema: {
              model: {
                id:"key",
                fields: {
                  key:{from:"key"},
                  label:{from:"label"},
                  labelL2:{from:"labelL2"},
                  labelL3:{from:"labelL3"},
                  module:{from:"module"},
                  line:{from:"line",type:"number"}
                }
              }
            }
        });

        $scope.updateI18nDataGridOptions = {
          dataSource: $scope.updateI18nDataDS,
          selectable: "row",
          filterable: true,
          sortable: true,
          scrollable: false,
          reorderable: true,
          resizable: true,
          pageable: {refresh: true,pageSizes: true,buttonCount: 5},
          editable: {
              mode: "popup",
              template: kendo.template($("#updateI18nDataTemplate").html())
          },
          toolbar: [{
              name: "create"
          }],
          edit:function (e) {

              if ($stateParams.menuItem.allowedAction === "Y") {
                  $(".k-state-disabled").addClass("k-grid-update").removeClass("k-state-disabled");
              } else {
                  $(".k-grid-update").addClass("k-state-disabled").removeClass("k-grid-update");
              }

              if (!e.model.isNew()) {
                $("#updateI18nDataID").data("kendoGrid").select("[data-uid="+e.model.uid+"]");
              }
              $("[data-bind='value:module_input']").removeAttr("data-bind");
              e.model.module_input = undefined;
              var editWindow = this.editable.element.data("kendoWindow");
              editWindow.wrapper.css({ width: "50%" });
              $(".k-edit-form-container").parent().data("kendoWindow").center();
          },
          save:function (e) {
              $("[data-bind='value:module_input']").removeAttr("data-bind");
              e.model.module_input = undefined;
              if (e.model.key != undefined && e.model.key != "") {
                  e.model.key = e.model.key.toUpperCase();
              }
          },
          columns: [
            {field:"key",title:$scope.Key},
            {field:"label",title:$scope.Label,template:"<div class='trText'>#: label #</div>"},
            {field:"labelL2",title:$scope.LabelL2,template:"<div class='trText'>#: labelL2 #</div>"},
            {field:"labelL3",title:$scope.LabelL3,template:"<div class='trText'>#: labelL3 #</div>"},
            {field:"module",title:$scope.Module},
            {field:"line",title:$scope.Line},
            {
              command:[
                {name: "edit", text:{edit: "",	update: "",	cancel: ""}},
                {template: "<a class='k-button k-button-icontext k-grid-Copy' style='min-width:16px;' ng-show="+($stateParams.menuItem.allowedAction=="Y"?true:false)+" ng-click=\'deleteClick(this)\'><span class='k-icon k-delete'></span></a>"}
              ],
              title: "&nbsp;",
              width:"5%"
            }
          ]

        }


        $scope.updateI18nDate = function () {
            kendo.ui.progress($('[ui-view="updateI18nDatatab"]'), true);
            var prefix = window.location.protocol + "//" + window.location.host;
            $http({
              method: 'POST',
              url: $scope.serviceRoot+"/i18nData/updateByPath",
              data: JSON.stringify({
                "label":prefix+"/app/i18n/locale.en-US.json",
                "labelL2":prefix+"/app/i18n/locale.zh-HK.json",
                "labelL3":prefix+"/app/i18n/locale.zh-CN.json"
              })
            }).success(function (data) {
              kendo.ui.progress($('[ui-view="updateI18nDatatab"]'), false);
               $scope.updateI18nDataDS.read();
              
            }).error(function () {
              kendo.ui.progress($('[ui-view="updateI18nDatatab"]'), false);
              parseDelException.alertdelmsg(request.status,$scope.ExceptionDialog,$scope.UpdateI18nDataError,$scope.confirmLabel);
            });
        }

        $scope.writeToPath = function () {
            kendo.ui.progress($('[ui-view="updateI18nDatatab"]'), true);
            $http({
              method: 'POST',
              url: $scope.serviceRoot+"/i18nData/writeToPath",
              data: JSON.stringify({key:"/var/lib/tomcat8/webapps/CmsApplication/WEB-INF/securityResources/app/i18n/lcoale.${language}.json.txt"})
              
            }).success(function (data) {
              kendo.ui.progress($('[ui-view="updateI18nDatatab"]'), false);
            }).error(function () {
              kendo.ui.progress($('[ui-view="updateI18nDatatab"]'), false);
              parseDelException.alertdelmsg(request.status,$scope.ExceptionDialog,$scope.UpdateI18nDataError,$scope.confirmLabel);
            });
        }




        $scope.deleteClick=function(selectedItem){
          var confirmDelete=prompt2({
            "title": $scope.WarningDialog,
            "message": $scope.DeleteItem,
            "buttons": [
              {
                "label": $scope.confirmLabel,
                "cancel": false,
                "primary": false
              },{
                "label": $scope.Cancel,
                "cancel": true,
                "primary": false
              }
            ]
          });
          confirmDelete.then(function(result){
            $http({
              method: 'POST',
              url: $rootScope.serviceRoot+"/i18nData/destroy",
              data:JSON.stringify(selectedItem.dataItem)
            }).then(function successCallback(response) {
              $scope.updateI18nDataDS.read();
            }, function errorCallback(response) {
              parseDelException.alertdelmsg(response,$scope.ExceptionDialog,$scope.APP2001,$scope.confirmLabel);
            });
          });
        }


      });

    });



  });

