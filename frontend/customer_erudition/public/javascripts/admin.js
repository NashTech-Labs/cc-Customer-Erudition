
var newBank = function(){
    $.get("/su/bank_form", function( data ) {
      $( "#su_content" ).html( data );
    });
}

var bankList = function(){
      $( "#su_content" ).html('<table id="myTable" class="table table-striped table-hover table-condensed"></table>');
      $.get("/su/all_banks", function( data ) {
            $('#myTable').DataTable( {
                      data: data,
                      "bFilter" : true,
                      "bLengthChange" : false,
                      "oLanguage": { "sSearch": '<i class="fa fa-search"></i>' },
                      "paging": false,
                      "destroy": true,
                      columns: [
                          { title: "Name", data: 'name' },
                          { title: "Branch", data: 'branch' },
                          { title: "IFSC Code", data: 'ifsc' }
                      ]
                  } );
          });

}

var newAgent= function(){
    $.get("/bank/agent_form", function( data ) {
        $( "#bank_content" ).html( data );
    });
}

var agentList = function(){
    $( "#bank_content" ).html('<table id="myTable" class="table table-striped table-hover table-condensed"></table>');
    $.get("/bank/all_agents", function( data ) {
        $('#myTable').DataTable( {
            data: data,
            "bFilter" : true,
            "bLengthChange" : false,
            "oLanguage": { "sSearch": '<i class="fa fa-search"></i>' },
            "paging": false,
            "destroy": true,
            columns: [
                { title: "Name", data: 'name' },
                { title: "Created By", data: 'createdBy' }
            ]
        } );
    });

}

var newCustomer= function(){
    $.get("/agent/customer_form", function( data ) {
        $( "#agent_content" ).html( data );
    });
}

var customerList = function(){
    $( "#agent_content" ).html('<table id="myTable" class="table table-striped table-hover table-condensed"></table>');
    $.get("/customer/all_customers", function( data ) {
        $('#myTable').DataTable( {
            data: data,
            "bFilter" : true,
            "bLengthChange" : false,
            "oLanguage": { "sSearch": '<i class="fa fa-search"></i>' },
            "paging": false,
            "destroy": true,
            columns: [
                { title: "Name", data: 'name' },
                { title: "AdharNo", data: 'adhar' }
            ]
        } );
    });

}

