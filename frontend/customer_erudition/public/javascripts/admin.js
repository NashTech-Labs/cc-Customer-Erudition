
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
