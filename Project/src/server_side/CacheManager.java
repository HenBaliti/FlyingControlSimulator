package server_side;

public interface CacheManager<Pro,Sol>{
	
	boolean existSolution(Pro var1);

    Sol loadSolution(Pro var1);

    void store(Pro var1, Sol var2);
}

