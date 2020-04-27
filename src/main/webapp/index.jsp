<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.JsonParser"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<html>
<head>
<title>Car Application</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">

</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">CarApplication</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="cardet.html">Add Car details</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>

	
	<table class="table">
		<thead class="thead-light">
			
		</thead>
		<tbody>

			<th scope="row">ID</th>
			<th scope="row">Model</th>
			<th scope="row">Brand</th>
			<th scope="row">Year</th>
			<th scope="row">Color</th>
			<th scope="row">Type</th>

<% 
   			  	final String API_URL = "http://localhost:8080/thirdd/carapi/cars";
   			
   				//Fetching data from API.
   				try{
					URL url = new URL(API_URL);
					HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
					httpURLConnection.setRequestMethod("GET");
					httpURLConnection.setDoInput(true); //Downloads.
					httpURLConnection.setDoOutput(false);//Uploads.
					
					InputStream inputStream = (InputStream)httpURLConnection.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					
					if(httpURLConnection.getResponseCode() == 200){
						
						//Reading the file line by line.
						
						String line = "";
						StringBuilder sb = new StringBuilder();
						
						while((line = bufferedReader.readLine()) != null){
							sb.append(line);
						}
						
						String jsonString = sb.toString();
						
						JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();	
						
						for(int i =0; i < jsonArray.size(); i++){
							JsonObject obj = jsonArray.get(i).getAsJsonObject();
							
							%>
								<tr >
									<td class="table-secondary"><% out.print(obj.get("id").getAsString()); %></td>
									<td class="table-success"><% out.print(obj.get("model").getAsString()); %></td>
									<td class="table-danger"><% out.print(obj.get("brand").getAsString()); %></td>
									<td class="table-warning"><% out.print(obj.get("year").getAsString()); %></td>
									<td class="table-info"><% out.print(obj.get("color").getAsString()); %></td>
									<td class="table-dark"><% out.print(obj.get("type").getAsString()); %></td>
								</tr>
							<% 
						}
						
					}
   					
   				}catch(Exception e){
   					e.printStackTrace();
   					
   					out.print("Error : "+e.getMessage());
   				}
    			
   			%>
		</tbody>
	</table>

</body>
</html>
