package raas;

/**
 * Deze class kan gebruikt worden als de API service niet meer beschikbaar wordt. De RD coordinaten uit het BRON bestand
 * worden hier omgezet naar WGS coordinaten.
 */
public class RDToWGS {
    Integer X0      = 155000;
    Integer Y0      = 463000;
    Double phi0    = 52.15517440;
    Double lam0    = 5.38720621;

    public RDToWGS() {

        Double coords[] = {146095.42, 422724.591};
        Double change[] = change(coords);
        for (Double i:  change) {
            System.out.println(i);
        }
        System.out.println(change.length);

    }

    public Double[] change(Double coords[]) {
        Integer Kp[] = {0,2,0,2,0,2,1,4,2,4,1};
        Integer Kq[] = {1,0,2,1,3,2,0,0,3,1,1};
        Double Kpq[] = {3235.65389,-32.58297,-0.24750,-0.84978,-0.06550,-0.01709,-0.00738,0.00530,-0.00039,0.00033,-0.00012};

        Integer Lp[] = {1,1,1,3,1,3,0,3,1,0,2,5};
        Integer Lq[] = {0,1,2,0,3,1,1,2,4,2,0,0};
        Double Lpq[] = {5260.52916,105.94684,2.45656,-0.81885,0.05594,-0.05607,0.01199,-0.00256,0.00128,0.00022,-0.00022,0.00026};

        double dX = 1E-5 * ( coords[0] - X0 );
        double dY = 1E-5 * ( coords[1] - Y0 );

        double phi = (double) 0;
        double lam = (double) 0;

        for (int i = 0; i < Kpq.length; i++) {
            phi = phi + ( Kpq[i] * Math.pow(dX,Kp[i]) * Math.pow(dY,Kq[i]) );
            phi = phi0 + phi / 3600;
        }

        for (int i = 0; i < Lpq.length; i++) {
            lam = lam + ( Lpq[i] * Math.pow(dX,Lp[i]) * Math.pow(dY,Lq[i]) );
            lam = lam0 + lam / 3600;
        }

        return new Double[] {phi, lam};
    }
}
