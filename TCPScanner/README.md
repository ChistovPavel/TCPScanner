<h1 align="center">Host and port range scan tool.</h1> 
<p>Tool gives scan result in JSON format.</p>
<p>Use next keys to set your input params:</p>

	[reqired]
	-h 	Set or range of hosts, that will be scanned.
		Set example:		-h 172.217.21.195, 172.217.21.196, 172.217.21.197
		Range Example:		-h 172.217.21.195-197, 172.217.21-23.195, 172.217-220.21.195
		Mixed use Example:	-h 172.217.21.195-197, 5.255.250.50, 172.217.21-23.195, 5.255.250.51
	
	[required]
	-p	Set or range of ports, that will be scanned. This set or range will be used for each host.
		Set example: 		-p 22, 80, 443, 8080
		Range example:		-p 22-80, 400-500
		Mixed use example:	-p 22-80, 8080, 400-500
	
	[optional]	
	-t	Number of threads for multithreaded scaning.
		Default value - 1 thread.
	
	[optinal]
	-T	Time in milliseconds, that will be spent on connection. 
		Default value - 5000 milliseconds.
	
	[optional]
	-P	Path to file to save the scan result.
		If path to file is not used, scan result will be printed to the output stream. 
		
	
<p>Test:</p>
<ul>
<li><p>Input params: 	-h 5.255.250-255.50-53, 172.217.21-23.195 -p 80-90, 430-450 -t 20 -T 1000 -P "Result.json"</p></li>
<li><p>Output params:	Scan result was saved to Result.json file.</p></li>
</ul>	
	